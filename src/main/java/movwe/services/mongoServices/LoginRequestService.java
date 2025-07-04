package movwe.services.mongoServices;

import lombok.AllArgsConstructor;
import movwe.domains.mongos.LoginRequest;
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

    public void create(LoginRequest loginRequest) {
        loginRequestRepository.save(loginRequest);
    }

    public void deleteAll() {
        loginRequestRepository.deleteAll();
    }
}
