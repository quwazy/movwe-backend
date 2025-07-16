package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movwe.domains.users.dtos.CreateUserDto;
import movwe.domains.users.dtos.UpdateUserDto;
import movwe.services.moderatorServices.UserService;
import movwe.utils.interfaces.ControllerInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
@RequestMapping(path = "/api/users")
public class UserController implements ControllerInterface<CreateUserDto, UpdateUserDto> {
    private final UserService userService;

    @Override
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(userService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserDto dto){
        try {
            if (userService.create(dto) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with creating user");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> update(@Valid @RequestBody UpdateUserDto dto) {
        try {
            if (userService.update(dto) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with updating user");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Changing user's active field")
    @PutMapping(path = "/active/{id}")
    public ResponseEntity<?> changeClientActive(@PathVariable Long id){
        try {
            if (userService.updateActivity(id) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with changing user's active field");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            if (userService.deleteById(id)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with deleting user");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAll() {
        try {
            userService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
