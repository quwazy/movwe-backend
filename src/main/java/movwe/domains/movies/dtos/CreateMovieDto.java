package movwe.domains.movies.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateMovieDto implements Serializable {
    @Schema(description = "Movie title")
    private String title;
    @Schema(description = "Your description of the movie")
    private String description;
}
