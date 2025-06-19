package movwe.services;

import lombok.AllArgsConstructor;
import movwe.domains.clients.dtos.ClientDto;
import movwe.domains.clients.dtos.CreateClientDto;
import movwe.domains.clients.entities.Client;
import movwe.domains.clients.mappers.ClientMapper;
import movwe.repositories.ClientRepository;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientService implements ServiceInterface<Client> {
    private ClientRepository clientRepository;

    @Override
    public DtoInterface get(Long id) {
        return ClientMapper.INSTANCE.fromClientToDto(clientRepository.findById(id).orElse(null));
    }

    @Override
    public List<ClientDto> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(ClientMapper.INSTANCE::fromClientToDto)
                .toList();
    }

    @Override
    public Client add(DtoInterface dto) {
        if (dto instanceof CreateClientDto createClientDto){
            return clientRepository.save(ClientMapper.INSTANCE.fromDtoToClient(createClientDto));
        }
        return null;
    }

    @Override
    public Client update(Long id, DtoInterface dto) {
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
