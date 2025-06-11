package movwe.domains.employees.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import movwe.domains.employees.enums.Role;
import movwe.domains.users.User;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "employees")
public class Employee extends User {
    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active;
}
