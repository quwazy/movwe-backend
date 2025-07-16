package movwe.domains.movies;

import movwe.domains.movies.dtos.UserMovieDto;
import movwe.domains.movies.dtos.CreateMovieDto;
import movwe.domains.movies.dtos.ModeratorMovieDto;
import movwe.domains.movies.dtos.UpdateMovieDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    UserMovieDto fromMovieToClientDto(Movie movie);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "movie.id", target = "movieId")
    ModeratorMovieDto fromMovieToModeratorMovieDto(Movie movie);

    Movie fromCreateDtoToMovie(CreateMovieDto createMovieDto);

    void fromUpdateDtoToMovie(UpdateMovieDto updateMovieDto, @MappingTarget Movie movie);
}
