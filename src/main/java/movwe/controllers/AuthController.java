package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import movwe.domains.mongos.LoginRequest;
import movwe.services.auth.JwtService;
import movwe.services.mongos.LoginRequestService;
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
@CrossOrigin(origins = "*")
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private LoginRequestService loginRequestService;

    @PostMapping(path = "/employee",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login employee")
    public ResponseEntity<?> loginEmployee(@RequestBody LoginDto loginDto) {
        return getResponseEntity(loginDto, "/employee");
    }

    @PostMapping(path = "/client",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Login client")
    public ResponseEntity<?> loginClient(@RequestBody LoginDto loginDto) {
        return getResponseEntity(loginDto, "/client");
    }

    private ResponseEntity<?> getResponseEntity(@RequestBody LoginDto loginDto, String route) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(loginDto.getEmail());
        loginRequest.setPassword(loginDto.getPassword());
        loginRequest.setRoute(route);
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()));

            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);
        } catch (Exception e) {
            loginRequest.setSuccessful(false);
            loginRequestService.save(loginRequest);
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        loginRequest.setSuccessful(true);
        loginRequestService.save(loginRequest);
        return ResponseEntity.ok(new JwtDto(jwtService.generateToken(loginDto.getEmail())));
    }
}
