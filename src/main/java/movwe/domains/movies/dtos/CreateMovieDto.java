package movwe.domains.movies.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents new movie for one client")
public class CreateMovieDto implements DtoInterface {
    @Schema(description = "Email of client")
    private String email;
    @Schema(description = "Movie title")
    private String title;
    @Schema(description = "Your description of the movie")
    private String description;
    private String yearOfRelease;
    private String trailerUrl;
    private String type;
    private String genre;
}
