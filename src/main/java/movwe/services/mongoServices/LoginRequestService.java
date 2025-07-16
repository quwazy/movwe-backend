package movwe.services.mongoServices;

import lombok.AllArgsConstructor;
import movwe.domains.mongoEntities.LoginRequest;
import movwe.repositories.mongoRepositories.LoginRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LoginRequestService {
    private final LoginRequestRepository loginRequestRepository;

    public List<LoginRequest> getAllByEmail(String email) {
        return loginRequestRepository.findAllByEmail(email).orElse(null);
    }

    public List<LoginRequest> getByEmailInLastDay(String email) {
        return loginRequestRepository.findByEmailAndRequestTimeAfterOrderByRequestTimeAsc(email, System.currentTimeMillis()/1000L - 60 * 60 * 24).orElse(null);
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
        loginRequestRepository.save(loginRequest);
    }

    public void deleteAll() {
        loginRequestRepository.deleteAll();
    }
}
