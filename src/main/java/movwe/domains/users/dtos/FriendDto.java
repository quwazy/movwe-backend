package movwe.domains.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents friend from fried list")
public class FriendDto implements DtoInterface {
    @NotBlank(message = "Username is required")
    private String username;
}
