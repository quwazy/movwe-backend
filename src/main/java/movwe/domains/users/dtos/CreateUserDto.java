package movwe.domains.users.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import movwe.domains.users.entities.Address;
import movwe.domains.users.entities.Info;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents new user")
public class CreateUserDto implements DtoInterface {
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
    @NotBlank(message = "Username is required")
    private String username;
    private Info info;
    private Address address;
}
