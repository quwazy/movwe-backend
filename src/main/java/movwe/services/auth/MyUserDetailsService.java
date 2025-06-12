package movwe.services.auth;

import lombok.AllArgsConstructor;
import movwe.domains.clients.entities.Client;
import movwe.domains.employees.entities.Employee;
import movwe.domains.mongos.LoginRequest;
import movwe.repositories.ClientRepository;
import movwe.repositories.EmployeeRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private ClientRepository clientRepository;
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);

        try {
            Client client = clientRepository.findByEmail(email).orElse(null);

            if (client != null) {
                return User
                        .withUsername(email)
                        .password(client.getPassword())
                        .roles("USER")
                        .build();
            } else {
                Optional<Employee> employee = employeeRepository.findByEmail(email);
                if (employee.isPresent() && employee.get().isActive()) {

                    return User
                            .withUsername(email)
                            .password(employee.get().getPassword())
                            .roles(employee.get().getRole().toString())
                            .build();
                }
            }

        } catch (Exception ex) {
            throw new UsernameNotFoundException("User with email: " + email + " not found!");
        }
        return null;
    }
}
