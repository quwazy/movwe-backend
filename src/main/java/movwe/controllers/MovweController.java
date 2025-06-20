package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import movwe.domains.movies.dtos.CreateMovieDto;
import movwe.services.MovieService;
import movwe.services.authServices.JwtService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/movwe")
public class MovweController {
    private final JwtService jwtService;
    private final MovieService movieService;

    @GetMapping(path = "/getAllMovies", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all movies from one client")
    public ResponseEntity<?> getAllMovies(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(movieService.getAllMovies(jwtService.extractEmail(token.substring(7))));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(path = "/addMovie", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Client adds new movie on his list")
    public ResponseEntity<?> addMovie(@RequestHeader("Authorization") String token, @RequestBody CreateMovieDto createMovieDto) {
        try {
            if (movieService.addMovie(jwtService.extractEmail(token.substring(7)),createMovieDto) != null) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with adding movie to client's list");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
