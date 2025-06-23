package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import movwe.services.MovieService;
import movwe.utils.interfaces.DtoInterface;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    @GetMapping(path = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get movie by its id")
    public ResponseEntity<?> getMovie(@PathVariable Long id) {
        try{
            Optional<DtoInterface> movieDto = movieService.get(id);
            if (movieDto.isPresent()) {
                return ResponseEntity.ok(movieDto.get());
            }
            return ResponseEntity.badRequest().body("Movie with id " + id + " does not exist");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all movies")
    public ResponseEntity<?> getAllMovies() {
        try {
            return ResponseEntity.ok(movieService.getAll());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/getAllByClient/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get movies from client, by email")
    public ResponseEntity<?> getAllMoviesByClient(@PathVariable String email) {
        try {
            return ResponseEntity.ok(movieService.getAllByClient(email));
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    @Operation(summary = "Delete movie by id")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        try {
            movieService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/deleteAll")
    @Operation(summary = "Delete all movies in database")
    public ResponseEntity<?> deleteAllMovies() {
        try {
            movieService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
