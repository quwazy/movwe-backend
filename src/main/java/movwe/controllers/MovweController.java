package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movwe.domains.movies.dtos.UpdateMovieDto;
import movwe.domains.users.dtos.FriendDto;
import movwe.domains.movies.dtos.CreateMovieDto;
import movwe.services.userServices.FriendService;
import movwe.services.authServices.JwtService;
import movwe.services.userServices.MovweService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/movwe")
public class MovweController {
    private final JwtService jwtService;
    private final MovweService movweService;
    private final FriendService friendService;

    @Operation(summary = "Search for users")
    @GetMapping(path = "/searchUsers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> searchUsers(@RequestHeader("Authorization") String token, @Valid @RequestBody FriendDto friendDto){
        try {
            return ResponseEntity.ok(friendService.searchUsers(extractUsernameFromJwt(token), friendDto.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Get all movies from user")
    @GetMapping(path = "/getUserMovies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserMovies(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(movweService.getUserMovies(extractUsernameFromJwt(token)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Get all movies from other user")
    @GetMapping(path = "/getOtherUserMovies", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOtherUserMovies(@RequestHeader("Authorization") String token, @Valid @RequestBody FriendDto friendDto) {
        try {
            return ResponseEntity.ok(movweService.getUserMovies(friendDto.getUsername()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "List of friends from user")
    @GetMapping(path = "/getFriendList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getFriendList(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(friendService.getFriendList(extractUsernameFromJwt(token)));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Add user to friend list")
    @PostMapping(path = "/addFriend", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addFriend(@RequestHeader("Authorization") String token, @Valid @RequestBody FriendDto friendDto){
        try {
            if (friendService.addFriendToFriendList(extractUsernameFromJwt(token), friendDto.getUsername()) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with adding friend to friend list");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Add new movie for user")
    @PostMapping(path = "/addMovie", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMovie(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateMovieDto createMovieDto) {
        try {
            if (movweService.addUserMovie(extractUsernameFromJwt(token), createMovieDto) != null) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with adding new movie");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Update existing movie")
    @PutMapping(path = "/updateMovie", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMovie(@RequestHeader("Authorization") String token, @Valid @RequestBody UpdateMovieDto updateMovieDto) {
        try {
            if (movweService.updateUserMovie(extractUsernameFromJwt(token), updateMovieDto) != null) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with updating existing movie");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Remove user from friend list")
    @DeleteMapping(path = "/removeFriend", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeFriend(@RequestHeader("Authorization") String token, @RequestBody FriendDto friendDto){
        try {
            if (friendService.removeFriendFromFriendList(extractUsernameFromJwt(token),friendDto.getUsername()) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with removing friend from friend list");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Delete movie from user's list")
    @DeleteMapping(path = "/deleteMovie/{id}")
    public ResponseEntity<?> deleteMovie(@RequestHeader("Authorization") String token, @PathVariable Long id){
        try {
            if (movweService.deleteUserMovie(extractUsernameFromJwt(token), id) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with deleting movie");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /* Extract username from jwt token */
    private String extractUsernameFromJwt(String jwt) {
        return jwtService.extractUsername(jwt.substring(7));
    }
}
