package movwe.domains.moderators.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents updated moderator")
public class UpdateModeratorDto implements DtoInterface {
    @NotNull(message = "Id is required")
    private Long id;
    @Email
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
