package movwe.repositories.mongos;

import movwe.domains.mongos.LoginRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRequestRepository extends MongoRepository<LoginRequest, String> {
}
