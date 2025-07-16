package movwe.services.authServices;

import lombok.AllArgsConstructor;
import movwe.domains.users.User;
import movwe.domains.moderators.Moderator;
import movwe.repositories.UserRepository;
import movwe.repositories.ModeratorRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModeratorRepository moderatorRepository;

    @Override
    @Cacheable(value = "userByEmail", key = "#email")
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent() && user.get().isActive()) {
                return org.springframework.security.core.userdetails.User
                        .withUsername(email)
                        .password(user.get().getPassword())
                        .roles("USER")
                        .build();
            } else {
                Optional<Moderator> moderator = moderatorRepository.findByEmail(email);
                if (moderator.isPresent() && moderator.get().isActive()) {
                    return org.springframework.security.core.userdetails.User
                            .withUsername(email)
                            .password(moderator.get().getPassword())
                            .roles(moderator.get().getRole().toString())
                            .build();
                }
            }
        } catch (Exception ex) {
            throw new UsernameNotFoundException("User with email: " + email + " not found!");
        }
        return null;
    }
}
