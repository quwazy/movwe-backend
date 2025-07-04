package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import movwe.domains.clients.dtos.FriendDto;
import movwe.domains.movies.dtos.CreateMovieDto;
import movwe.services.FriendService;
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
    private final FriendService friendService;

    @Operation(summary = "Get all movies from client's list")
    @GetMapping(path = "/getAllMovies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllMovies(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(movieService.getAllMoviesFromClient(extractEmailFromJwt(token)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Add new movie on client's list")
    @PostMapping(path = "/addMovie", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMovie(@RequestHeader("Authorization") String token, @RequestBody CreateMovieDto createMovieDto) {
        try {
            createMovieDto.setEmail(extractEmailFromJwt(token));
            if (movieService.create(createMovieDto) != null) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with adding movie to client's list");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete movie from client's list")
    @DeleteMapping(path = "/deleteMovie/{id}")
    public ResponseEntity<?> deleteMovie(@RequestHeader("Authorization") String token, @PathVariable Long id){
        try {
            if (movieService.deleteClientMovies(id, extractEmailFromJwt(token))){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with deleting movie from client's list");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "List all friends from friend list")
    @GetMapping(path = "/getFriendList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFriendList(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(friendService.getFriendList(extractEmailFromJwt(token)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Add friend on friend list")
    @PostMapping(path = "/addFriend", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addFriend(@RequestHeader("Authorization") String token, @RequestBody FriendDto friendDto){
        try {
            if (!friendService.addFriend(extractEmailFromJwt(token), friendDto.getUsername()).isEmpty()){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with adding friend to friend list");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Remove friend from friend list")
    @DeleteMapping(path = "/removeFriend", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeFriend(@RequestHeader("Authorization") String token, @RequestBody FriendDto friendDto){
        try {
            if (friendService.removeFriend(extractEmailFromJwt(token),friendDto.getUsername())){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with removing friend from friend list");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Extract email from jwt token
     * @param jwt token
     * @return email from jwt as String
     */
    private String extractEmailFromJwt(String jwt) {
        return jwtService.extractEmail(jwt.substring(7));
    }
}
