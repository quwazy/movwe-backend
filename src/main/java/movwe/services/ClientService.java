package movwe.services;

import lombok.AllArgsConstructor;
import movwe.repositories.ClientRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientService {
    private ClientRepository clientRepository;
}
