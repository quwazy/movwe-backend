package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movwe.domains.users.dtos.CreateUserDto;
import movwe.services.moderatorServices.LoginRequestService;
import movwe.services.moderatorServices.UserService;
import movwe.services.authServices.JwtService;
import movwe.utils.dtos.JwtDto;
import movwe.utils.dtos.LoginDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final LoginRequestService loginRequestService;
    private final JwtService jwtService;
    private final UserService userService;

    @Operation(summary = "Login moderator")
    @PostMapping(path = "/moderator",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginModerator(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request) {
        return getResponseEntity(loginDto,"/moderator", extractRequestIp(request));
    }

    @Operation(summary = "Login user")
    @PostMapping(path = "/user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDto loginDto, HttpServletRequest request) {
        return getResponseEntity(loginDto,"/user", extractRequestIp(request));
    }

    @Operation(summary = "Sign in user")
    @PostMapping(path = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        if (userService.create(createUserDto) != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with adding user");
    }

    /**
     * Checks if a moderator/user is authenticated,
     * also saves a login attempt
     * @param loginDto email and password as dto
     * @param route which URL called this method
     * @param ipAddress of request
     * @return JWT if login is successful, or bad request if something is wrong
     */
    private ResponseEntity<?> getResponseEntity(@RequestBody LoginDto loginDto, String route, String ipAddress) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword())
            );
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
        } catch (Exception e) {
            loginRequestService.create(loginDto.getEmail(), loginDto.getPassword(), route, ipAddress, false);
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        loginRequestService.create(loginDto.getEmail(), loginDto.getPassword(), route, ipAddress, true);

        if (route.equals("/user")){
            return ResponseEntity.ok(new JwtDto(jwtService.generateToken(loginDto.getEmail(), userService.getByEmail(loginDto.getEmail()).getUsername())));
        }
        else {
            return ResponseEntity.ok(new JwtDto(jwtService.generateToken(loginDto.getEmail(), "moderator")));
        }
    }

    /* Extracts ip address from request */
    private String extractRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            ip = ip.split(",")[0].trim(); // X-Forwarded-For might contain a list of IPs: client, proxy1, proxy2
        }
        return ip;
    }
}
