package movwe.services;

import lombok.AllArgsConstructor;
import movwe.domains.clients.dtos.ClientDto;
import movwe.domains.clients.dtos.CreateClientDto;
import movwe.domains.clients.dtos.FriendDto;
import movwe.domains.clients.entities.Client;
import movwe.domains.clients.mappers.ClientMapper;
import movwe.repositories.ClientRepository;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService implements ServiceInterface {
    private ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Cacheable(value = "client", key = "#id", unless = "#result == null")
    public Optional<DtoInterface> get(Long id) {
        return clientRepository.findById(id).map(ClientMapper.INSTANCE::fromClientToDto);
    }

    @Override
    @Cacheable(value = "clients")
    public List<ClientDto> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(ClientMapper.INSTANCE::fromClientToDto)
                .toList();
    }

    public Optional<Client> getByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    public Optional<Client> getByUsername(String username) {
        return clientRepository.findByUsername(username);
    }

    @Override
    @CacheEvict(value = "clients", allEntries = true)
    @CachePut(value = "client", key = "result.id", condition = "#dto != null", unless = "#result == null")
    public DtoInterface add(DtoInterface dto) {
        if (dto instanceof CreateClientDto createClientDto){
            Client client = ClientMapper.INSTANCE.fromDtoToClient(createClientDto);
            client.setPassword(passwordEncoder.encode(createClientDto.getPassword()));
            return ClientMapper.INSTANCE.fromClientToDto(clientRepository.save(client));
        }
        return null;
    }

    public List<FriendDto> addFriend(String email, String friendUsername){
        Optional<Client> optionalClient = clientRepository.findByEmail(email);
        Optional<Client> optionalFriend = clientRepository.findByUsername(friendUsername);

        if (optionalClient.isPresent() && optionalFriend.isPresent()) {
            Client client = optionalClient.get();
            Client friend = optionalFriend.get();
            client.getFriends().add(friend);
            clientRepository.save(client);
            return client.getFriends()
                    .stream()
                    .map(ClientMapper.INSTANCE::fromClientToFriendDto)
                    .toList();
        } else {
            return Collections.emptyList();
        }

    }

    @Override
    public DtoInterface update(Long id, DtoInterface dto) {
        return null;
    }

    @Caching(evict = {
            @CacheEvict(value = "clinet", key = "#id"),
            @CacheEvict(value = "clients", allEntries = true),
            @CacheEvict(value = "userByEmail", allEntries = true)
    })
    @CachePut(value = "client", key = "#id", condition = "#result != null ")
    public DtoInterface changeActive(Long id) {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setActive(!client.isActive());
                    Client saved = clientRepository.save(client);
                    return ClientMapper.INSTANCE.fromClientToDto(saved);
                })
                .orElse(null);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "client", key = "#id"),
            @CacheEvict(value = "clients", allEntries = true),
            @CacheEvict(value = "userByEmail", allEntries = true)
    })
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "client", allEntries = true),
            @CacheEvict(value = "clients", allEntries = true),
            @CacheEvict(value = "userByEmail", allEntries = true)
    })
    public void deleteAll() {
        clientRepository.deleteAll();
    }

    public void removeFriend(String email, String friendUsername){
        Optional<Client> optionalClient = clientRepository.findByEmail(email);
        Optional<Client> optionalFriend = clientRepository.findByUsername(friendUsername);

        if (optionalClient.isPresent() && optionalFriend.isPresent()) {
            Client client = optionalClient.get();
            client.getFriends().remove(optionalFriend.get());
            clientRepository.saveAndFlush(client);
            System.out.println("REMOVED");
        }
    }
}
