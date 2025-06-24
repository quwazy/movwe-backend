package movwe.utils.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "Represents login request, for Employee and Client")
public class LoginDto implements Serializable {
    @Schema(description = "Email of client or employee")
    private String email;
    @Schema(description = "Password of client or employee")
    private String password;
}
