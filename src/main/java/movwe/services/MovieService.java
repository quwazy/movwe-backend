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
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieService implements ServiceInterface<Movie> {
    private final MovieRepository movieRepository;
    private final ClientService clientService;

    public List<ClientMovieDto> getAllMoviesFromClient(String email) {
        return movieRepository.findAllByClient(clientService.getByEmail(email))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(MovieMapper.INSTANCE::fromMovieToDto)
                .collect(Collectors.toList());
    }

    public boolean deleteClientMovies(Long id, String email) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie != null && movie.getClient().getEmail().equals(email)) {
            movieRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Movie getById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    @Deprecated(since = "Doesn't work in this service. Instead use getAllByEmail method")
    public Movie getByEmail(String email) {
        return null;
    }

    public List<EmployeeMovieDto> getAllByEmail(String email) {
        return movieRepository.findAllByClient(clientService.getByEmail(email))
                .orElseGet(Collections::emptyList)
                .stream()
                .map(MovieMapper.INSTANCE::fromMovieToEmployeeMovieDto)
                .toList();
    }

    @Override
    public List<EmployeeMovieDto> getAll() {
        return movieRepository.findAll()
                .stream()
                .map(MovieMapper.INSTANCE::fromMovieToEmployeeMovieDto)
                .toList();
    }

    @Override
    public Movie create(DtoInterface dto) {
        if (dto instanceof CreateMovieDto createMovieDto && createMovieDto.getEmail() != null){
            Movie movie = MovieMapper.INSTANCE.fromDtoToMovie(createMovieDto);
            Client client = clientService.getByEmail(createMovieDto.getEmail());
            if (client == null || movie == null) {
                return null;
            }
            movie.setClient(client);
            return movieRepository.save(movie);
        }
        return null;
    }

    @Override
    public Movie update(DtoInterface dto) {
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        return movieRepository.deleteByIdCustom(id) == 1;
    }

    @Override
    public boolean deleteByEmail(String email) {
        return movieRepository.deleteAllByClient_Email(email) >= 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        movieRepository.deleteAll();
    }
}
