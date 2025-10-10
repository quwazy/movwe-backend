package movwe.configs;

import lombok.AllArgsConstructor;
import movwe.domains.users.User;
import movwe.domains.moderators.Moderator;
import movwe.domains.moderators.enums.Role;
import movwe.repositories.UserRepository;
import movwe.repositories.ModeratorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class DataConfig implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ModeratorRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        /// ovde popuni sifrarnike na kraju

        Optional<Moderator> adminEmployee = employeeRepository.findByEmail("jane06.ristic@gmail.com");
        if (adminEmployee.isEmpty()) {
            Moderator moderator = new Moderator();
            moderator.setRole(Role.ADMIN);
            moderator.setEmail("jane06.ristic@gmail.com");
            moderator.setPassword(passwordEncoder.encode("12345"));
            moderator.setFirstName("Janko");
            moderator.setLastName("Ristic");
            employeeRepository.saveAndFlush(moderator);
        }

        Optional<User> testClient = userRepository.findByEmail("test@gmail.com");
        if (testClient.isEmpty()) {
            User user = new User();
            user.setEmail("test@gmail.com");
            user.setPassword(passwordEncoder.encode("12345"));
            user.setUsername("test");
            userRepository.saveAndFlush(user);
        }

        Optional<User> testFriendClient = userRepository.findByEmail("testfriend@gmail.com");
        if (testFriendClient.isEmpty()){
            User user = new User();
            user.setEmail("testfriend@gmail.com");
            user.setPassword(passwordEncoder.encode("12345"));
            user.setUsername("testfriend");
            userRepository.saveAndFlush(user);
        }
    }
}
