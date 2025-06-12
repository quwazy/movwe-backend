package movwe.configs;

import lombok.AllArgsConstructor;
import movwe.domains.clients.entities.Client;
import movwe.domains.employees.entities.Employee;
import movwe.domains.employees.enums.Role;
import movwe.repositories.ClientRepository;
import movwe.repositories.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class DataConfig implements CommandLineRunner {
    private ClientRepository clientRepository;
    private EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        /// ovde popuni sifrarnike na kraju
//        if (clientRepository.count() == 0) {
//            Client client = new Client();
//            client.setEmail("jane@gmail.com");
//            client.setUsername("klijent");
//            client.setAddress("jasenicka");
//            clientRepository.saveAndFlush(client);
//        }
        Optional<Employee> employee = employeeRepository.findByEmail("jane06.ristic@gmail.com");
        if (employee.isEmpty()) {
            Employee employee1 = new Employee();
            employee1.setRole(Role.ADMIN);
            employee1.setEmail("jane06.ristic@gmail.com");
            employee1.setPassword(passwordEncoder.encode("12345"));
            employee1.setFirstName("Janko");
            employee1.setLastName("Ristic");
            employeeRepository.saveAndFlush(employee1);
        }
    }
}
