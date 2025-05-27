package movwe.domains.movies;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
public class Movie implements Serializable {
    @Id
    private Long id;
    private String title;
    private String description;
}
