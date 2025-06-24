package movwe.domains.movies.mappers;

import movwe.domains.movies.dtos.ClientMovieDto;
import movwe.domains.movies.dtos.CreateMovieDto;
import movwe.domains.movies.dtos.EmployeeMovieDto;
import movwe.domains.movies.entities.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface MovieMapper {
    MovieMapper INSTANCE = Mappers.getMapper(MovieMapper.class);

    ClientMovieDto fromMovieToDto(Movie movie);

    Movie fromDtoToMovie(CreateMovieDto dto);

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.email", target = "clientEmail")
    @Mapping(source = "movie.id", target = "movieId")
    EmployeeMovieDto fromMovieToEmployeeMovieDto(Movie movie);
}
