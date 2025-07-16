package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movwe.domains.moderators.dtos.CreateModeratorDto;
import movwe.domains.moderators.dtos.UpdateModeratorDto;
import movwe.services.moderatorServices.ModeratorService;
import movwe.utils.interfaces.ControllerInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "/api/moderators")
public class ModeratorController implements ControllerInterface<CreateModeratorDto, UpdateModeratorDto> {
    private final ModeratorService moderatorService;

    @Override
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(moderatorService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(moderatorService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> create(@Valid @RequestBody CreateModeratorDto dto) {
        try {
            if (moderatorService.create(dto) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with creating moderator");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> update(@Valid @RequestBody UpdateModeratorDto dto) {
        try {
            if (moderatorService.update(dto) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with updating moderator");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Changing moderator's active field")
    @PutMapping(path = "/active/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable Long id){
        try {
            if (moderatorService.updateActivity(id) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with changing moderator's active field");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            if (moderatorService.deleteById(id)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with deleting moderator");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAll() {
        try {
            moderatorService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
