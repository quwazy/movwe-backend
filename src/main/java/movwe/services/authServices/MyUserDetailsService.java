package movwe.services.authServices;

import lombok.AllArgsConstructor;
import movwe.domains.clients.entities.Client;
import movwe.domains.employees.entities.Employee;
import movwe.repositories.ClientRepository;
import movwe.repositories.EmployeeRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    @Cacheable(value = "userByEmail", key = "#email")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Optional<Client> client = clientRepository.findByEmail(email);
            if (client.isPresent() && client.get().isActive()) {
                return User
                        .withUsername(email)
                        .password(client.get().getPassword())
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
