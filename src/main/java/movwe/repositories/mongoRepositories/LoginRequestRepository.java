package movwe.repositories.mongoRepositories;

import movwe.domains.mongos.LoginRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoginRequestRepository extends MongoRepository<LoginRequest, String> {
    Optional<List<LoginRequest>> findAllByEmail(String email);
    Optional<List<LoginRequest>> findByEmailAndRequestTimeAfterOrderByRequestTimeAsc(String email, Long requestTime);
}
