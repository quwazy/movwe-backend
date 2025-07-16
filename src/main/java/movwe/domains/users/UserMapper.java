package movwe.domains.users;

import movwe.domains.users.dtos.UpdateUserDto;
import movwe.domains.users.dtos.UserDto;
import movwe.domains.users.dtos.CreateUserDto;
import movwe.domains.users.dtos.FriendDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto fromUserToDto(User user);

    User fromCreateDtoToUser(CreateUserDto createUserDto);

    void fromUpdateDtoToUser(UpdateUserDto updateUserDto, @MappingTarget User user);

    FriendDto fromUserToFriendDto(User user);
}
