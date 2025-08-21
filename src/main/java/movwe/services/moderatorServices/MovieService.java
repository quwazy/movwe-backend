package movwe.services.moderatorServices;

import lombok.AllArgsConstructor;
import movwe.domains.movies.Movie;
import movwe.domains.movies.dtos.ModeratorMovieDto;
import movwe.domains.movies.MovieMapper;
import movwe.domains.movies.dtos.UpdateMovieDto;
import movwe.repositories.MovieRepository;
import movwe.utils.exceptions.IdNotFoundException;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class MovieService implements ServiceInterface<ModeratorMovieDto> {
    private final MovieRepository movieRepository;

    @Override
    @Transactional(readOnly = true)
    public ModeratorMovieDto getById(Long id) {
        return movieRepository.findById(id)
                .map(MovieMapper.INSTANCE::fromMovieToModeratorMovieDto)
                .orElseThrow(() -> new IdNotFoundException("Movie", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModeratorMovieDto> getAll() {
        return movieRepository.findAll()
                .stream()
                .map(MovieMapper.INSTANCE::fromMovieToModeratorMovieDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ModeratorMovieDto> getAllByUserId(Long id) {
        return movieRepository.findAllByUser_IdOrderByCreationDateDesc(id)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(MovieMapper.INSTANCE::fromMovieToModeratorMovieDto)
                .toList();
    }

    @Override
    public ModeratorMovieDto create(DtoInterface dto) {
        return null;
    }

    @Override
    public ModeratorMovieDto update(DtoInterface dto) {
        if (dto instanceof UpdateMovieDto updateMovieDto){
            Movie movie = movieRepository.findById(updateMovieDto.getId()).orElseThrow(() -> new IdNotFoundException("Movie", updateMovieDto.getId()));
            MovieMapper.INSTANCE.fromUpdateDtoToMovie(updateMovieDto, movie);
            return MovieMapper.INSTANCE.fromMovieToModeratorMovieDto(movieRepository.save(movie));
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteById(Long id) {
        return movieRepository.deleteByIdCustom(id) == 1;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean deleteAllByUserId(Long id) {
        return movieRepository.deleteAllByUser_Id(id) >= 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        movieRepository.deleteAll();
    }
}
