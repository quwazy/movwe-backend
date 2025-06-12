package movwe.utils.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Schema(description = "Represents JWT token for logged in employee/client")
public class JwtDto implements Serializable {
    private String token;
}
