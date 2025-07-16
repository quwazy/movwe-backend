package movwe.services.userServices;

import lombok.AllArgsConstructor;
import movwe.domains.users.dtos.FriendDto;
import movwe.domains.users.User;
import movwe.domains.users.UserMapper;
import movwe.repositories.UserRepository;
import movwe.utils.exceptions.UsernameNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class FriendService {
    private final UserRepository userRepository;

    public List<FriendDto> searchUsers(String username, String search) {
        return userRepository.findUsersByUsernameStartingWith(search+"%", username, PageRequest.of(0, 5))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(UserMapper.INSTANCE::fromUserToFriendDto)
                .toList();
    }

    public List<FriendDto> getFriendList(String username) {
        return userRepository.findByUsernameAndFetchFriendList(username).orElseThrow(() -> new UsernameNotFoundException(username))
                .getFriendList()
                .stream()
                .map(UserMapper.INSTANCE::fromUserToFriendDto)
                .toList();
    }

    public List<FriendDto> addFriendToFriendList(String username, String friendUsername) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndFetchFriendList(username).orElseThrow(() -> new UsernameNotFoundException(username));
        User friend = userRepository.findByUsername(friendUsername).orElseThrow(() -> new UsernameNotFoundException(friendUsername));

        if (!user.getFriendList().contains(friend)) {
            user.getFriendList().add(friend);
            userRepository.save(user);
        }
        return user.getFriendList()
                .stream()
                .map(UserMapper.INSTANCE::fromUserToFriendDto)
                .toList();
    }

    public List<FriendDto> removeFriendFromFriendList(String username, String friendUsername) {
        User user = userRepository.findByUsernameAndFetchFriendList(username).orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found!"));
        User friend = userRepository.findByUsername(friendUsername).orElseThrow(() -> new UsernameNotFoundException("User with username: " + friendUsername + " not found!"));

        if (user.getFriendList().contains(friend)) {
            user.getFriendList().remove(friend);
            userRepository.save(user);
        }
        return user.getFriendList()
                .stream()
                .map(UserMapper.INSTANCE::fromUserToFriendDto)
                .toList();
    }
}
