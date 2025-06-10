package movwe.domains.employees.dtos;

import lombok.Data;
import movwe.utils.interfaces.DtoInterface;

@Data
public class CreateEmployeeDto implements DtoInterface {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
}
