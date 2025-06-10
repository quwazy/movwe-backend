package movwe.configs;

import lombok.AllArgsConstructor;
import movwe.domains.clients.Client;
import movwe.repositories.ClientRepository;
import movwe.repositories.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataConfig implements CommandLineRunner {
    private ClientRepository clientRepository;
    private EmployeeRepository employeeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (clientRepository.count() == 0) {
            Client client = new Client();
            client.setEmail("jane@gmail.com");
            client.setUsername("klijent");
            client.setAddress("jasenicka");
            clientRepository.saveAndFlush(client);
        }
    }
}
