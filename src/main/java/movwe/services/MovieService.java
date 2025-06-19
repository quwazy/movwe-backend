package movwe.services;

import lombok.AllArgsConstructor;
import movwe.domains.movies.entities.Movie;
import movwe.repositories.MovieRepository;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService implements ServiceInterface<Movie> {
    private final MovieRepository movieRepository;

    @Override
    public DtoInterface get(Long id) {
        return null;
    }

    @Override
    public List<?> getAll() {
        return List.of();
    }

    @Override
    public Movie add(DtoInterface dto) {
        return null;
    }

    @Override
    public Movie update(Long id, DtoInterface dto) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }

    @Override
    public void deleteAll() {
    }
}
