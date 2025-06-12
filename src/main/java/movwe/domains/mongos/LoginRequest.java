package movwe.domains.mongos;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "login_requests")
public class LoginRequest implements Serializable {
    @Id
    private String id;
    private String email;
    private String password;
    private String route;
    private boolean successful;
    private Long requestTime = System.currentTimeMillis() / 1000L;
}
