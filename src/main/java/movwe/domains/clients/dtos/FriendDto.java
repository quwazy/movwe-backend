package movwe.domains.clients.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents friend from fried list")
public class FriendDto implements DtoInterface {
    private String username;
}
