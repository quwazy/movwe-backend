package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import movwe.domains.clients.dtos.CreateClientDto;
import movwe.services.ClientService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/clients")
public class ClientController {
    private final ClientService clientService;

    @GetMapping(path = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get client by id")
    public ResponseEntity<?> getClient(@PathVariable Long id) {
        try {
            if (id == null){
                return ResponseEntity.badRequest().build();
            }
            if (clientService.get(id) != null){
                return ResponseEntity.ok(clientService.get(id));
            }
            return ResponseEntity.badRequest().body("Client with id " + id + " does not exist");
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
            if (clientService.add(createClientDto).isActive()){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

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
