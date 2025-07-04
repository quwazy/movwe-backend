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

        Optional<Employee> adminEmployee = employeeRepository.findByEmail("jane06.ristic@gmail.com");
        if (adminEmployee.isEmpty()) {
            Employee employee = new Employee();
            employee.setRole(Role.ADMIN);
            employee.setEmail("jane06.ristic@gmail.com");
            employee.setPassword(passwordEncoder.encode("12345"));
            employee.setFirstName("Janko");
            employee.setLastName("Ristic");
            employeeRepository.saveAndFlush(employee);
        }

        Optional<Employee> editorEmployee = employeeRepository.findByEmail("ogi@gmail.com");
        if (editorEmployee.isEmpty()) {
            Employee employee = new Employee();
            employee.setRole(Role.EDITOR);
            employee.setEmail("ogi@gmail.com");
            employee.setPassword(passwordEncoder.encode("12345"));
            employee.setFirstName("Ogi");
            employee.setLastName("Stojanovic");
            employeeRepository.saveAndFlush(employee);
        }

        Optional<Client> testClient = clientRepository.findByEmail("test@gmail.com");
        if (testClient.isEmpty()) {
            Client client = new Client();
            client.setEmail("test@gmail.com");
            client.setPassword(passwordEncoder.encode("12345"));
            client.setUsername("test");
            clientRepository.saveAndFlush(client);
        }

        Optional<Client> testFriendClient = clientRepository.findByEmail("testfriend@gmail.com");
        if (testFriendClient.isEmpty()){
            Client client = new Client();
            client.setEmail("testfriend@gmail.com");
            client.setPassword(passwordEncoder.encode("12345"));
            client.setUsername("testfriend");
            clientRepository.saveAndFlush(client);
        }
    }
}
