package movwe.services;

import lombok.AllArgsConstructor;
import movwe.domains.clients.dtos.ClientDto;
import movwe.domains.clients.dtos.CreateClientDto;
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

import java.util.List;

@Service
@AllArgsConstructor
public class ClientService implements ServiceInterface<Client> {
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client getById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    @Override
    @Cacheable(value = "client", key = "#email", unless = "#result == null")
    public Client getByEmail(String email) {
        return clientRepository.findByEmail(email).orElse(null);
    }

    public Client getByUsername(String username) {
        return clientRepository.findByUsername(username).orElse(null);
    }

    @Override
    @Cacheable(value = "clients")
    public List<ClientDto> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(ClientMapper.INSTANCE::fromClientToDto)
                .toList();
    }

    @Override
    @CacheEvict(value = "clients", allEntries = true)
    @CachePut(value = "client", key = "#result.email", unless = "#result == null")
    public Client create(DtoInterface dto){
        if (dto instanceof CreateClientDto createClientDto){
            Client client = ClientMapper.INSTANCE.fromDtoToClient(createClientDto);
            client.setPassword(passwordEncoder.encode(createClientDto.getPassword()));
            return clientRepository.save(client);
        }
        return null;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "client", key = "#result.email"),
            @CacheEvict(value = "clients", allEntries = true)
    })
    @CachePut(value = "client", key = "#result.email", unless = "#result == null")
    public Client update(DtoInterface dto) {
        return null;
    }

    @Caching(evict = {
            @CacheEvict(value = "client", key = "#email"),
            @CacheEvict(value = "clients", allEntries = true),
            @CacheEvict(value = "userByEmail", key = "#email")
    })
    @CachePut(value = "client", key = "#email", unless = "#result == null")
    public Client updateActivity(String email) {
        return clientRepository.findByEmail(email)
                .map(client -> {
                    client.setActive(!client.isActive());
                    return clientRepository.save(client);
                })
                .orElse(null);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "client", allEntries = true),
            @CacheEvict(value = "clients", allEntries = true),
            @CacheEvict(value = "userByEmail", allEntries = true),
            @CacheEvict(value = "friends", allEntries = true)
    })
    public boolean deleteById(Long id) {
        return clientRepository.deleteByIdCustom(id) == 1;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "client", key = "#email"),
            @CacheEvict(value = "clients", allEntries = true),
            @CacheEvict(value = "userByEmail", key = "#email"),
            @CacheEvict(value = "friends", key = "#email")
    })
    public boolean deleteByEmail(String email){
        return clientRepository.deleteByEmail(email) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "client", allEntries = true),
            @CacheEvict(value = "clients", allEntries = true),
            @CacheEvict(value = "userByEmail", allEntries = true),
            @CacheEvict(value = "friends", allEntries = true)
    })
    public void deleteAll() {
        clientRepository.deleteAll();
    }
}
