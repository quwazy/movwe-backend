package movwe.domains.movies.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
public class CreateMovieDto implements DtoInterface {
    @Schema(description = "Movie title")
    private String title;
    @Schema(description = "Your description of the movie")
    private String description;
}
