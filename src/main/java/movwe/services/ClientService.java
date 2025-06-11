package movwe.services;

import lombok.AllArgsConstructor;
import movwe.domains.clients.dtos.CreateClientDto;
import movwe.domains.clients.mappers.ClientMapper;
import movwe.repositories.ClientRepository;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientService implements ServiceInterface {
    private ClientRepository clientRepository;

    @Override
    public DtoInterface get(Long id) {
        return ClientMapper.INSTANCE.fromClientToDto(clientRepository.findById(id).orElse(null));
    }

    @Override
    public List<?> getAll() {
        return clientRepository.findAll()
                .stream()
                .map(ClientMapper.INSTANCE::fromClientToDto)
                .toList();
    }

    @Override
    public boolean add(DtoInterface dto) {
        if (dto instanceof CreateClientDto createClientDto){
            clientRepository.save(ClientMapper.INSTANCE.fromDtoToClient(createClientDto));
        }
        return false;
    }

    @Override
    public boolean update(Long id, DtoInterface dto) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAll() {
        if (clientRepository.count() != 0) {
            clientRepository.deleteAll();
            return true;
        }
        return false;
    }
}
