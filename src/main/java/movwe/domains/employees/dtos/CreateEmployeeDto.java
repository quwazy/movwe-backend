package movwe.domains.employees.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents new employee")
public class CreateEmployeeDto implements DtoInterface {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
}
