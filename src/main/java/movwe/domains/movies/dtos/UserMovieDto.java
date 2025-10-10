package movwe.domains.movies.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents movie from database for user")
public class UserMovieDto implements DtoInterface {
    @Schema(description = "Id of movie")
    private Long id;
    private String title;
    private String description;
    private String yearOfRelease;
    private String trailerUrl;
    private String type;
    private String genre;
}
