package movwe.domains.clients.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import movwe.domains.clients.embedded.Address;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents new client")
public class CreateClientDto implements DtoInterface {
    private String email;
    private String password;
    private String username;
    private Address address;
}
