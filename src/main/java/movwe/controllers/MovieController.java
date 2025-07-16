package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movwe.domains.movies.dtos.CreateMovieDto;
import movwe.domains.movies.dtos.UpdateMovieDto;
import movwe.services.moderatorServices.MovieService;
import movwe.utils.interfaces.ControllerInterface;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
@RequestMapping("/api/movies")
public class MovieController implements ControllerInterface<CreateMovieDto, UpdateMovieDto> {
    private final MovieService movieService;

    @Override
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movieService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(movieService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Get all movies from user with id")
    @GetMapping(path = "/getAllByUserId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllByUserId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movieService.getAllByUserId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> create(@Valid @RequestBody CreateMovieDto dto) {
        try {
            if (movieService.create(dto) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with creating movie");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> update(UpdateMovieDto dto) {
        try {
            if (movieService.update(dto) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with updating movie");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            if (movieService.deleteById(id)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with deleting movie");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Deleting all the movies from user with id")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/deleteAllByUserId/{id}")
    public ResponseEntity<?> deleteByEmail(@PathVariable Long id) {
        try {
            if (movieService.deleteAllByUserId(id)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with deleting movies from user with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAll() {
        try {
            movieService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
