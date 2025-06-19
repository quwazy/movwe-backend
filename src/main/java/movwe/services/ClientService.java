package movwe.services;

import lombok.AllArgsConstructor;
import movwe.domains.clients.dtos.ClientDto;
import movwe.domains.clients.dtos.CreateClientDto;
import movwe.domains.clients.entities.Client;
import movwe.domains.clients.mappers.ClientMapper;
import movwe.repositories.ClientRepository;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientService implements ServiceInterface {
    private ClientRepository clientRepository;

    @Override
    @Cacheable(value = "client", key = "#id")
    public DtoInterface get(Long id) {
        return ClientMapper.INSTANCE.fromClientToDto(clientRepository.findById(id).orElse(null));
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

    @Override
    public void delete(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        }
    }

    @Override
    public void deleteAll() {
        if (clientRepository.count() != 0) {
            clientRepository.deleteAll();
        }
    }
}
