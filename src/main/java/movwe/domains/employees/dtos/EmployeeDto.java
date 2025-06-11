package movwe.domains.employees.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
@Schema(description = "Represents employee entity from data base")
public class EmployeeDto implements DtoInterface {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
}
