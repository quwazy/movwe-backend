package movwe.repositories;

import movwe.domains.logins.LoginRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRequestRepository extends JpaRepository<LoginRequest, Long> {

    Optional<LoginRequest> findAllByEmail(String email);

    Optional<LoginRequest> findByEmailAndRequestTimeAfterOrderByRequestTimeAsc(String email, Long requestTime);
}
