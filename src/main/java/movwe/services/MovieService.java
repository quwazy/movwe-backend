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
    public boolean add(DtoInterface dto) {
        return false;
    }

    @Override
    public boolean update(Long id, DtoInterface dto) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        return false;
    }
}
