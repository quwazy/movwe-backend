package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import movwe.domains.clients.dtos.CreateClientDto;
import movwe.domains.mongos.LoginRequest;
import movwe.services.ClientService;
import movwe.services.authServices.JwtService;
import movwe.services.mongoServices.LoginRequestService;
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
    private final JwtService jwtService;
    private final LoginRequestService loginRequestService;
    private final ClientService clientService;

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

    @PostMapping(path = "/addClient", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add new client")
    public ResponseEntity<?> addClient(@RequestBody CreateClientDto createClientDto) {
        try {
            if (clientService.addClient(createClientDto) != null) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with adding client");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Function which checks if employee/client is authenticated and
     * saves a login attempt in database
     * @param loginDto email and password as dto
     * @param route which URL called this method
     * @return JWT if login is successful, or bad request if something is wrong
     */
    private ResponseEntity<?> getResponseEntity(@RequestBody LoginDto loginDto, String route) {
        /// Login attempt
        LoginRequest loginRequest = LoginRequest.builder()
                .email(loginDto.getEmail())
                .password(loginDto.getPassword())
                .route(route)
                .requestTime(System.currentTimeMillis()/1000L)
                .build();

        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword())
            );
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
