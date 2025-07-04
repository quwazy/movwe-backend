package movwe.services;

import lombok.AllArgsConstructor;
import movwe.domains.clients.dtos.FriendDto;
import movwe.domains.clients.entities.Client;
import movwe.domains.clients.mappers.ClientMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class FriendService {
    private final ClientService clientService;

    @Cacheable(value = "friends", key = "#email")
    public List<FriendDto> getFriendList(String email) {
        return clientService.getByEmail(email).getFriends()
                .stream()
                .map(ClientMapper.INSTANCE::fromClientToFriendDto)
                .toList();
    }

    @CacheEvict(value = "friends", key = "#email")
    @CachePut(value = "friends", key = "#email", unless = "#result == null")
    public List<FriendDto> addFriend(String email, String friendUsername) {
        Client client = clientService.getByEmail(email);
        Client friend = clientService.getByUsername(friendUsername);

        if (client != null && friend != null) {
            client.getFriends().add(friend);
            return clientService.save(client).getFriends()
                    .stream()
                    .map(ClientMapper.INSTANCE::fromClientToFriendDto)
                    .toList();
        } else {
            return Collections.emptyList();
        }
    }

    @CacheEvict(value = "friends", key = "#email")
    public boolean removeFriend(String email, String friendUsername) {
        Client client = clientService.getByEmail(email);
        Client friend = clientService.getByUsername(friendUsername);

        if (client != null && friend != null) {
            client.getFriends().remove(friend);
            clientService.save(client);
            return true;
        }
        return false;
    }
}
