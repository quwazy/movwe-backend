package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import movwe.domains.movies.dtos.CreateMovieDto;
import movwe.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@AllArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @Operation(summary = "Get all movies")
    @GetMapping
    public ResponseEntity<?> getMovies() {
        return ResponseEntity.ok("Movies");
    }

    @Operation(summary = "Create a movie")
    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody CreateMovieDto createMovieDto) {
        System.out.println(createMovieDto.toString());
        return ResponseEntity.ok("Movie created");
    }
}
