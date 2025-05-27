package movwe.services;

import lombok.AllArgsConstructor;
import movwe.repositories.MovieRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
}
