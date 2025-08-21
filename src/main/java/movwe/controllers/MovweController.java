package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movwe.domains.movies.dtos.UpdateMovieDto;
import movwe.domains.movies.dtos.UserMovieDto;
import movwe.domains.users.dtos.FriendDto;
import movwe.domains.movies.dtos.CreateMovieDto;
import movwe.services.userServices.FriendService;
import movwe.services.authServices.JwtService;
import movwe.services.userServices.MovweService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/movwe")
public class MovweController {
    private final JwtService jwtService;
    private final MovweService movweService;
    private final FriendService friendService;

    @Operation(summary = "Search for users")
    @GetMapping(path = "/searchUsers", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FriendDto>> searchUsers(@RequestHeader("Authorization") String token, @Valid @RequestBody FriendDto friendDto){
        return ResponseEntity.ok(friendService.searchUsers(extractUsernameFromJwt(token), friendDto.getUsername()));
    }

    @Operation(summary = "Get all movies from user")
    @GetMapping(path = "/getUserMovies", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserMovieDto>> getUserMovies(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(movweService.getUserMovies(extractUsernameFromJwt(token)));
    }

    @Operation(summary = "Get all movies from other user")
    @GetMapping(path = "/getOtherUserMovies", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserMovieDto>> getOtherUserMovies(@RequestHeader("Authorization") String token, @Valid @RequestBody FriendDto friendDto) {
        return ResponseEntity.ok(movweService.getUserMovies(friendDto.getUsername()));
    }

    @Operation(summary = "List of friends from user")
    @GetMapping(path = "/getFriendList", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FriendDto>> getFriendList(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(friendService.getFriendList(extractUsernameFromJwt(token)));
    }

    @Operation(summary = "Add user to friend list")
    @PostMapping(path = "/addFriend", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addFriend(@RequestHeader("Authorization") String token, @Valid @RequestBody FriendDto friendDto){
        if (friendService.addFriendToFriendList(extractUsernameFromJwt(token), friendDto.getUsername()) != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with adding friend to friend list");
    }

    @Operation(summary = "Add new movie for user")
    @PostMapping(path = "/addMovie", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addMovie(@RequestHeader("Authorization") String token, @Valid @RequestBody CreateMovieDto createMovieDto) {
        if (movweService.addUserMovie(extractUsernameFromJwt(token), createMovieDto) != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with adding new movie");
    }

    @Operation(summary = "Update existing movie")
    @PutMapping(path = "/updateMovie", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateMovie(@RequestHeader("Authorization") String token, @Valid @RequestBody UpdateMovieDto updateMovieDto) {
        if (movweService.updateUserMovie(extractUsernameFromJwt(token), updateMovieDto) != null) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with updating existing movie");
    }

    @Operation(summary = "Remove user from friend list")
    @DeleteMapping(path = "/removeFriend", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> removeFriend(@RequestHeader("Authorization") String token, @RequestBody FriendDto friendDto){
        if (friendService.removeFriendFromFriendList(extractUsernameFromJwt(token),friendDto.getUsername()) != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with removing friend from friend list");
    }

    @Operation(summary = "Delete movie from user's list")
    @DeleteMapping(path = "/deleteMovie/{id}")
    public ResponseEntity<?> deleteMovie(@RequestHeader("Authorization") String token, @PathVariable Long id){
        if (movweService.deleteUserMovie(extractUsernameFromJwt(token), id) != null){
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().body("Something went wrong with deleting movie");
    }

    /* Extract username from jwt token */
    private String extractUsernameFromJwt(String jwt) {
        return jwtService.extractUsername(jwt.substring(7));
    }
}
