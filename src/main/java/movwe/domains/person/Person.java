package movwe.domains.person;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private String identifier;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, updatable = false)
    private String salt;

    @Column(columnDefinition = "BOOLEAN DEFAULT true")
    private boolean active = true;

    private Long creationDate = System.currentTimeMillis() / 1000L;

    @PrePersist
    public void prePersist() {
        if (identifier == null) {
            this.identifier = UUID.randomUUID().toString();
        }
        this.salt = generateSalt();
        this.creationDate = System.currentTimeMillis() / 1000L;
    }

    private String generateSalt() {
        byte[] saltBytes = new byte[16];
        new SecureRandom().nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }
}
