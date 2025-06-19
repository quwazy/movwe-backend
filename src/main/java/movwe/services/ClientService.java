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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService implements ServiceInterface {
    private ClientRepository clientRepository;

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

    @Override
    @CacheEvict(value = "clients", allEntries = true)
    @CachePut(value = "client", key = "result.id", condition = "#dto != null", unless = "#result == null")
    public DtoInterface add(DtoInterface dto) {
        if (dto instanceof CreateClientDto createClientDto){
            return ClientMapper.INSTANCE.fromClientToDto(clientRepository.save(ClientMapper.INSTANCE.fromDtoToClient(createClientDto)));
        }
        return null;
    }

    @Override
    public DtoInterface update(Long id, DtoInterface dto) {
        return null;
    }

    @Caching(evict = {
            @CacheEvict(value = "clinet", key = "#id"),
            @CacheEvict(value = "clients", allEntries = true)
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
            @CacheEvict(value = "employees", allEntries = true)
    })
    public void delete(Long id) {
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "clients", allEntries = true),
            @CacheEvict(value = "client", allEntries = true)
    })
    public void deleteAll() {
        clientRepository.deleteAll();
    }
}
