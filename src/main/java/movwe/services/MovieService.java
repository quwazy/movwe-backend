package movwe.services;

import lombok.AllArgsConstructor;
import movwe.domains.clients.entities.Client;
import movwe.domains.movies.dtos.ClientMovieDto;
import movwe.domains.movies.dtos.CreateMovieDto;
import movwe.domains.movies.dtos.EmployeeMovieDto;
import movwe.domains.movies.entities.Movie;
import movwe.domains.movies.mappers.MovieMapper;
import movwe.repositories.MovieRepository;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService implements ServiceInterface {
    private final MovieRepository movieRepository;
    private final ClientService clientService;

    @Override
    public Optional<DtoInterface> get(Long id) {
        return movieRepository.findById(id).map(MovieMapper.INSTANCE::fromMovieToEmployeeMovieDto);
    }

    @Override
    public List<EmployeeMovieDto> getAll() {
        return movieRepository.findAll().stream().map(MovieMapper.INSTANCE::fromMovieToEmployeeMovieDto).toList();
    }

    public List<EmployeeMovieDto> getAllByClient(String email) {
        return movieRepository.findAllByClient(clientService.getByEmail(email).orElse(null)).stream().map(MovieMapper.INSTANCE::fromMovieToEmployeeMovieDto).toList();
    }

    /**
     * Get all movies by client's email
     * @param email of client
     * @return list of movies
     * */
    public List<ClientMovieDto> getAllMovies(String email) {
        return movieRepository.findAllByClient(clientService.getByEmail(email).orElse(null))
                .stream()
                .map(MovieMapper.INSTANCE::fromMovieToDto)
                .toList();
    }

    @Override
    public DtoInterface add(DtoInterface dto) {
        return null;
    }

    public DtoInterface addMovie(String email, CreateMovieDto createMovieDto) {
        if (email != null && createMovieDto != null){
            Movie movie = MovieMapper.INSTANCE.fromDtoToMovie(createMovieDto);
            Client client = clientService.getByEmail(email).orElse(null);
            if (client != null) {
                movie.setClient(client);
                return MovieMapper.INSTANCE.fromMovieToDto(movieRepository.save(movie));
            }
            return null;
        }
        return null;
    }

    @Override
    public DtoInterface update(Long id, DtoInterface dto) {
        return null;
    }

    @Override
    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    public void deleteAllByClientEmail(String email) {
        movieRepository.deleteAllByClient_Email(email);
    }

    @Override
    public void deleteAll() {
        movieRepository.deleteAll();
    }
}
