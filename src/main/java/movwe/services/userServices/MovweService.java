package movwe.services.userServices;

import lombok.AllArgsConstructor;
import movwe.domains.movies.Movie;
import movwe.domains.movies.MovieMapper;
import movwe.domains.movies.dtos.CreateMovieDto;
import movwe.domains.movies.dtos.UpdateMovieDto;
import movwe.domains.movies.dtos.UserMovieDto;
import movwe.domains.users.User;
import movwe.repositories.MovieRepository;
import movwe.repositories.UserRepository;
import movwe.utils.exceptions.IdNotFoundException;
import movwe.utils.exceptions.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovweService {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public List<UserMovieDto> getUserMovies(String username) {
        return getAllMoviesFromUser(username);
    }

    public List<UserMovieDto> addUserMovie(String username, CreateMovieDto createMovieDto) {
        Movie movie = MovieMapper.INSTANCE.fromCreateDtoToMovie(createMovieDto);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found!"));
        if (user == null || movie == null) {
            return null;
        }

        movie.setUser(user);
        movieRepository.save(movie);
        return getAllMoviesFromUser(username);
    }

    public List<UserMovieDto> updateUserMovie(String username, UpdateMovieDto updateMovieDto) {
        Movie movie = movieRepository.findById(updateMovieDto.getId()).orElseThrow(() -> new IdNotFoundException("Movie", updateMovieDto.getId()));
        if (movie == null || !movie.getUser().getUsername().equals(username)) {
            return null;
        }

        MovieMapper.INSTANCE.fromUpdateDtoToMovie(updateMovieDto, movie);
        movieRepository.save(movie);
        return getAllMoviesFromUser(username);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<UserMovieDto> deleteUserMovie(String username, Long id) {
        if (movieRepository.deleteByIdAndUser_Username(id, username) == 1){
            return getAllMoviesFromUser(username);
        }
        return null;
    }

    private List<UserMovieDto> getAllMoviesFromUser(String username){
        return movieRepository.findAllByUser_UsernameOrderByCreationDateDesc(username)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(MovieMapper.INSTANCE::fromMovieToClientDto)
                .collect(Collectors.toList());
    }
}
