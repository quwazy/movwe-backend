package movwe.services.mongos;

import lombok.AllArgsConstructor;
import movwe.domains.mongos.LoginRequest;
import movwe.repositories.mongos.LoginRequestRepository;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class LoginRequestService {
    private final LoginRequestRepository loginRequestRepository;

    public void save(LoginRequest loginRequest) {
        loginRequestRepository.save(loginRequest);
    }
}
