package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import movwe.domains.clients.entities.Client;
import movwe.domains.clients.mappers.ClientMapper;
import movwe.services.ClientService;
import movwe.utils.interfaces.ControllerInterface;
import movwe.utils.interfaces.DtoInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
@RequestMapping(path = "/api/clients")
public class ClientController implements ControllerInterface {
    private final ClientService clientService;

    @Operation(summary = "Changing client's active field")
    @PutMapping(path = "/active/{email}")
    public ResponseEntity<?> changeClientActive(@PathVariable String email){
        try {
            if (clientService.updateActivity(email) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with changing client active field");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            Client client = clientService.getById(id);
            if (client != null) {
                return ResponseEntity.ok(ClientMapper.INSTANCE.fromClientToDto(client));
            }
            return ResponseEntity.badRequest().body("Something went wrong with get client by id " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        try{
            Client client = clientService.getByEmail(email);
            if (client != null) {
                return ResponseEntity.ok(ClientMapper.INSTANCE.fromClientToDto(client));
            }
            return ResponseEntity.badRequest().body("Something went wrong with get client by email " + email);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(clientService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> create(@RequestBody DtoInterface dto){
        try {
            if (clientService.create(dto) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with creating client");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> update(DtoInterface dto) {
        return null;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            if (clientService.deleteById(id)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with deleting client");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteByEmail(@PathVariable String email) {
        try {
            if (clientService.deleteByEmail(email)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with deleting client");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAll() {
        try {
            clientService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
