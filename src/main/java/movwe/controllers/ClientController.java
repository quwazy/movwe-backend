package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import movwe.domains.clients.dtos.CreateClientDto;
import movwe.domains.clients.entities.Client;
import movwe.domains.clients.mappers.ClientMapper;
import movwe.services.ClientService;
import movwe.utils.interfaces.DtoInterface;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
@RequestMapping(path = "/api/clients")
public class ClientController {
    private final ClientService clientService;

    @GetMapping(path = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get client by id")
    public ResponseEntity<?> getClient(@PathVariable Long id) {
        try {
            Optional<DtoInterface> clientDto = clientService.get(id);
            if (clientDto.isPresent()) {
                return ResponseEntity.ok(clientDto.get());
            } else {
                return ResponseEntity.badRequest().body("Client with id " + id + " does not exist");
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/getByEmail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get client by email")
    public ResponseEntity<?> getClientByEmail(@PathVariable String email) {
        try {
            Client client = clientService.getByEmail(email);
            if (client != null) {
                return ResponseEntity.ok(ClientMapper.INSTANCE.fromClientToDto(client));
            }
            return ResponseEntity.badRequest().body("Employee with email " + email + " does not exist");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all clients")
    public ResponseEntity<?> getAllClients() {
        try {
            return ResponseEntity.ok(clientService.getAll());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add new client")
    public ResponseEntity<?> addClient(@RequestBody CreateClientDto createClientDto){
        try {
            if (clientService.addClient(createClientDto) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with adding client");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping(path = "/active/{email}")
    @Operation(summary = "changing client's active field")
    public ResponseEntity<?> changeClientActive(@PathVariable String email){
        try {
            if (clientService.updateActivity(email) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/delete/{id}")
    @Operation(summary = "Delete client by id")
    public ResponseEntity<?> deleteClient(@PathVariable Long id) {
        try {
            clientService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/deleteByEmail/{email}")
    @Operation(summary = "Delete client by email")
    public ResponseEntity<?> deleteClientByEmail(@PathVariable String email) {
        try {
            clientService.deleteByEmail(email);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/deleteAll")
    @Operation(summary = "Delete all clients in table")
    public ResponseEntity<?> deleteAllClients() {
        try {
            clientService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
