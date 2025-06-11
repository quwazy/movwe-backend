package movwe.domains.movies.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import movwe.domains.clients.entities.Client;
import movwe.domains.movies.enums.Genre;
import movwe.domains.movies.enums.Type;

import java.io.Serializable;

@Entity
@Data
@Table(name = "movies")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private String title;

    private String description;

    @Column(length = 4)
    @Size(min = 4, max = 4)
    private String yearOfRelease;

    private String trailerUrl;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Genre genre;
}
