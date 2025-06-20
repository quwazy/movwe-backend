package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import movwe.domains.clients.dtos.CreateClientDto;
import movwe.services.ClientService;
import movwe.utils.interfaces.DtoInterface;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
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
            if (clientService.add(createClientDto) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/active/{id}")
    @Operation(summary = "changing client's active field")
    public ResponseEntity<?> changeClientActive(@PathVariable Long id){
        try {
            if (clientService.changeActive(id) != null){
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
            this.clientService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/deleteAll")
    @Operation(summary = "Delete all clients in table")
    public ResponseEntity<?> deleteAllClients() {
        try {
            this.clientService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
