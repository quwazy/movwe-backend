package movwe.domains.moderators.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import movwe.domains.moderators.enums.Role;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents moderator from database")
public class ModeratorDto implements DtoInterface {
    private Long id;
    private String email;
    private boolean active;
    private String firstName;
    private String lastName;
    private Role role;
}
