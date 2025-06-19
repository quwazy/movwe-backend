package movwe.services;

import lombok.AllArgsConstructor;
import movwe.repositories.MovieRepository;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MovieService implements ServiceInterface {
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
    public DtoInterface add(DtoInterface dto) {
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
