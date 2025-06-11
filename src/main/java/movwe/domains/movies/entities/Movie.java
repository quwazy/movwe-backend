package movwe.domains.movies.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import movwe.domains.movies.enums.Type;

import java.io.Serializable;

@Entity
@Data
@Table(name = "movies")
public class Movie implements Serializable {
    @Id
    private Long id;

    private Long clientId;

    private String title;

    private String description;

    @Column(length = 4)
    @Size(min = 4, max = 4)
    private String yearOfRelease;

    private String trailerUrl;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Type genre;
}
