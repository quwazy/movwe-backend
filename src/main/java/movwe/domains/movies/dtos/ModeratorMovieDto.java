package movwe.domains.movies.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents movie from database for moderatos")
public class ModeratorMovieDto implements DtoInterface {
    @Schema(description = "Id of user")
    private Long userId;
    @Schema(description = "Email of user")
    private String userEmail;
    @Schema(description = "Id of movie")
    private Long movieId;
    private String title;
    private String description;
    private String yearOfRelease;
    private String trailerUrl;
    private String type;
    private String genre;
}
