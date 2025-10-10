package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movwe.domains.users.dtos.CreateUserDto;
import movwe.domains.users.dtos.UpdateUserDto;
import movwe.domains.users.dtos.UserDto;
import movwe.services.moderatorServices.UserService;
import movwe.utils.interfaces.ControllerInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
@RequestMapping(path = "/api/users")
public class UserController implements ControllerInterface<CreateUserDto, UpdateUserDto> {
    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @Override
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    @Override
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserDto dto){
        if (userService.create(dto) != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with creating user");
    }

    @Override
    public ResponseEntity<?> update(@Valid @RequestBody UpdateUserDto dto) {
        if (userService.update(dto) != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with updating user");
    }

    @Operation(summary = "Changing user's active field")
    @PutMapping(path = "/active/{id}")
    public ResponseEntity<?> changeClientActive(@PathVariable Long id){
        if (userService.updateActivity(id) != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with changing user's active field");
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (userService.deleteById(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with deleting user");
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll() {
        userService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
