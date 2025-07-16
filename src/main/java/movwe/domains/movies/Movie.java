package movwe.domains.movies;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import movwe.domains.users.User;
import movwe.domains.movies.enums.Genre;
import movwe.domains.movies.enums.Type;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    private String description;

    @Column(length = 4)
    private Integer yearOfRelease;

    private String trailerUrl;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private Long creationDate = System.currentTimeMillis() / 1000L;

    @PrePersist
    public void prePersist() {
        this.creationDate = System.currentTimeMillis() / 1000L;
    }
}
