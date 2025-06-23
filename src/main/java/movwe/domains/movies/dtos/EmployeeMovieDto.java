package movwe.domains.movies.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents employee movie entity from database")
public class EmployeeMovieDto implements DtoInterface {
    private Long clientId;
    private String clientEmail;
    private Long movieId;
    private String title;
    private String description;
    private String yearOfRelease;
    private String trailerUrl;
    private String type;
    private String genre;
}
