package movwe.domains.clients.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents client entity from data base")
public class ClientDto implements DtoInterface {
    private Long id;
    private String email;
    private String username;
    private boolean active;
}
