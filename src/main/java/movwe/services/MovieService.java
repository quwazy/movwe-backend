package movwe.services;

import lombok.AllArgsConstructor;
import movwe.domains.clients.entities.Client;
import movwe.domains.movies.dtos.ClientMovieDto;
import movwe.domains.movies.dtos.CreateMovieDto;
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
        return null;
    }

    @Override
    public List<?> getAll() {
        return List.of();
    }

    public List<ClientMovieDto> getAllMovies(String email){
        return movieRepository.findAllByClient(clientService.getByEmail(email).orElse(null))
                .stream()
                .map(MovieMapper.INSTANCE::fromMovieToDto)
                .toList();
    }

    @Override
    public DtoInterface add(DtoInterface dto) {
        return null;
    }

    public DtoInterface addMovie(String email, CreateMovieDto createMovieDto){
        if (email != null && createMovieDto != null){
            Movie movie = MovieMapper.INSTANCE.fromDtoToMovie(createMovieDto);
            movie.setClient(clientService.getByEmail(email).orElse(null));
            return MovieMapper.INSTANCE.fromMovieToDto(movieRepository.save(movie));
        }
        return null;
    }

    @Override
    public DtoInterface update(Long id, DtoInterface dto) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public void deleteAll() {
    }
}
