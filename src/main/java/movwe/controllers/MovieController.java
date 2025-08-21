package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movwe.domains.movies.dtos.CreateMovieDto;
import movwe.domains.movies.dtos.ModeratorMovieDto;
import movwe.domains.movies.dtos.UpdateMovieDto;
import movwe.services.moderatorServices.MovieService;
import movwe.utils.interfaces.ControllerInterface;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
@RequestMapping("/api/movies")
public class MovieController implements ControllerInterface<CreateMovieDto, UpdateMovieDto> {
    private final MovieService movieService;

    @Override
    public ResponseEntity<ModeratorMovieDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getById(id));
    }

    @Override
    public ResponseEntity<List<ModeratorMovieDto>> getAll() {
        return ResponseEntity.ok(movieService.getAll());
    }

    @Operation(summary = "Get all movies from user with id")
    @GetMapping(path = "/getAllByUserId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ModeratorMovieDto>> getAllByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(movieService.getAllByUserId(id));
    }

    @Override
    public ResponseEntity<?> create(@Valid @RequestBody CreateMovieDto dto) {
        if (movieService.create(dto) != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with creating movie");
    }

    @Override
    public ResponseEntity<?> update(UpdateMovieDto dto) {
        if (movieService.update(dto) != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with updating movie");
    }

    @Override
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        if (movieService.deleteById(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with deleting movie");
    }

    @Operation(summary = "Deleting all the movies from user with id")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/deleteAllByUserId/{id}")
    public ResponseEntity<?> deleteByEmail(@PathVariable Long id) {
        if (movieService.deleteAllByUserId(id)){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with deleting movies from user with id: " + id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll() {
        movieService.deleteAll();
        return ResponseEntity.ok().build();
    }
}
