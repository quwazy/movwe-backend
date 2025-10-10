package movwe.domains.movies.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents new movie")
public class CreateMovieDto implements DtoInterface {
    @NotBlank(message = "Title is required")
    @Schema(description = "Movie title")
    private String title;
    @Schema(description = "Your description of the movie")
    private String description;
    private String yearOfRelease;
    private String trailerUrl;
    @NotBlank(message = "Movie type is required")
    private String type;
    @NotBlank(message = "Movie genre is required")
    private String genre;
}
