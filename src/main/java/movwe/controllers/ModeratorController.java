package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movwe.domains.moderators.dtos.CreateModeratorDto;
import movwe.domains.moderators.dtos.ModeratorDto;
import movwe.domains.moderators.dtos.UpdateModeratorDto;
import movwe.services.moderatorServices.ModeratorService;
import movwe.utils.interfaces.ControllerInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "/api/moderators")
public class ModeratorController implements ControllerInterface<CreateModeratorDto, UpdateModeratorDto> {
    private final ModeratorService moderatorService;

    @Override
    public ResponseEntity<ModeratorDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(moderatorService.getById(id));
    }

    @Override
    public ResponseEntity<List<ModeratorDto>> getAll() {
        return ResponseEntity.ok(moderatorService.getAll());
    }

    @Override
    public ResponseEntity<?> create(@Valid @RequestBody CreateModeratorDto dto) {
        if (moderatorService.create(dto) != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with creating moderator");
    }

    @Override
    public ResponseEntity<?> update(@Valid @RequestBody UpdateModeratorDto dto) {
        if (moderatorService.update(dto) != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with updating moderator");
    }

    @Operation(summary = "Changing moderator's active field")
    @PutMapping(path = "/active/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable Long id){
        if (moderatorService.updateActivity(id) != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with changing moderator's active field");
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (moderatorService.deleteById(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with deleting moderator");
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll() {
        moderatorService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
