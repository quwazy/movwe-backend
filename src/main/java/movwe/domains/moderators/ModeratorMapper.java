package movwe.domains.moderators;

import movwe.domains.moderators.dtos.CreateModeratorDto;
import movwe.domains.moderators.dtos.ModeratorDto;
import movwe.domains.moderators.dtos.UpdateModeratorDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ModeratorMapper {
    ModeratorMapper INSTANCE = Mappers.getMapper(ModeratorMapper.class);

    ModeratorDto fromModeratorToDto(Moderator moderator);

    Moderator fromCreteDtoToModerator(CreateModeratorDto createModeratorDto);

    void fromUpdateDtoToModerator(UpdateModeratorDto updateModeratorDto, @MappingTarget Moderator moderator);
}
