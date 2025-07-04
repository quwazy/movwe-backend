package movwe.controllers;

import lombok.AllArgsConstructor;
import movwe.domains.movies.entities.Movie;
import movwe.domains.movies.mappers.MovieMapper;
import movwe.services.MovieService;
import movwe.utils.interfaces.ControllerInterface;
import movwe.utils.interfaces.DtoInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN') or hasRole('EDITOR')")
@RequestMapping("/api/movies")
public class MovieController implements ControllerInterface {
    private final MovieService movieService;

    @Override
    public ResponseEntity<?> getById(Long id) {
        try {
            Movie movie = movieService.getById(id);
            if (movie != null) {
                return ResponseEntity.ok(MovieMapper.INSTANCE.fromMovieToDto(movie));
            }
            return ResponseEntity.badRequest().body("Something went wrong with get movie by id " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getByEmail(String email) {
        try {
            return ResponseEntity.ok(movieService.getAllByEmail(email));
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

    @Override
    public ResponseEntity<?> create(DtoInterface dto) {
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
    public ResponseEntity<?> update(DtoInterface dto) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        try {
            if (movieService.deleteById(id)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with deleting movie");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteByEmail(String email) {
        try {
            if (movieService.deleteByEmail(email)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with deleting movies from client with email " + email);
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
