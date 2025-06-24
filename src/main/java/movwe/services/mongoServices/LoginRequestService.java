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

    public void save(LoginRequest loginRequest) {
        loginRequestRepository.save(loginRequest);
    }

    /**
     * Login requests with
     * @param email you want to check
     * @return list of login requests
     */
    public List<LoginRequest> findAllByEmail(String email) {
        return loginRequestRepository.findAllByEmail(email).orElse(null);
    }

    /**
     * All requests with email in the last 24 hours, sorted by ascendancy
     * @param email you want to check
     * @return list of login request
     */
    public List<LoginRequest> findByEmailInLastDay(String email) {
        return loginRequestRepository.findByEmailAndRequestTimeAfterOrderByRequestTimeAsc(email, System.currentTimeMillis()/1000L - 60 * 60 * 24).orElse(null);
    }

    /**
     * Empty repository
     */
    public void deleteAll() {
        loginRequestRepository.deleteAll();
    }
}
