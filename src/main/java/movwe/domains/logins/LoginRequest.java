package movwe.domains.logins;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "login_requests")
public class LoginRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String route;

    private String ipAddress;

    private boolean successful;

    private Long requestTime = System.currentTimeMillis() / 1000L;

    @PrePersist
    public void prePersist() {
        this.requestTime = System.currentTimeMillis() / 1000L;
    }
}
