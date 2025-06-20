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

    @Cacheable(value = "clientByEmail", key = "#email", unless = "#result == null")
    public Optional<Client> getByEmail(String email) {
        return clientRepository.findByEmail(email);
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
            @CacheEvict(value = "employee", key = "#id"),
            @CacheEvict(value = "employees", allEntries = true),
            @CacheEvict(value = "userByEmail", allEntries = true)
    })
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "clients", allEntries = true),
            @CacheEvict(value = "client", allEntries = true),
            @CacheEvict(value = "userByEmail", allEntries = true)
    })
    public void deleteAll() {
        clientRepository.deleteAll();
    }
}
