package movwe.controllers;

import lombok.AllArgsConstructor;
import movwe.services.MovieService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<?> getMovies() {
        return ResponseEntity.ok("Movies");
    }

    @PostMapping
    public ResponseEntity<?> createMovie() {
        return ResponseEntity.ok("Movie created");
    }
}
