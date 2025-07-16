package movwe.domains.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents user from database")
public class UserDto implements DtoInterface {
    private Long id;
    private String email;
    private String username;
    private boolean active;
}
