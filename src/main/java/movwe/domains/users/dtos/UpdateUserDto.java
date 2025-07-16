package movwe.domains.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embedded;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import movwe.domains.users.entities.Address;
import movwe.domains.users.entities.Info;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents updated user")
public class UpdateUserDto implements DtoInterface {
    @NotNull(message = "Id is required")
    private Long id;
    @Email
    private String email;
    private String password;
    @Embedded
    private Info info;
    @Embedded
    private Address address;
}
