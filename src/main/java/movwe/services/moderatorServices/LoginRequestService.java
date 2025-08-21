package movwe.services.moderatorServices;

import lombok.AllArgsConstructor;
import movwe.domains.logins.LoginRequest;
import movwe.repositories.LoginRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoginRequestService {
    private final LoginRequestRepository loginRequestRepository;

    public List<LoginRequest> getAllByEmail(String email) {
        return loginRequestRepository.findAllByEmail(email).stream().toList();
    }

    public List<LoginRequest> getByEmailInLastDay(String email) {
        return loginRequestRepository.findByEmailAndRequestTimeAfterOrderByRequestTimeAsc(email, System.currentTimeMillis()/1000L - 60 * 60 * 24).stream().toList();
    }

    public void create(String email, String password, String route, String ipAddress, boolean isSuccess) {
        LoginRequest loginRequest = LoginRequest.builder()
                .email(email)
                .password(password)
                .route(route)
                .ipAddress(ipAddress)
                .requestTime(System.currentTimeMillis()/1000L)
                .successful(isSuccess)
                .build();
        loginRequestRepository.saveAndFlush(loginRequest);
    }

    public void deleteAll() {
        loginRequestRepository.deleteAll();
    }
}
