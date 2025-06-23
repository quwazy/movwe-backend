package movwe.domains.clients.mappers;

import movwe.domains.clients.dtos.ClientDto;
import movwe.domains.clients.dtos.CreateClientDto;
import movwe.domains.clients.dtos.FriendDto;
import movwe.domains.clients.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client fromDtoToClient(CreateClientDto dto);

    ClientDto fromClientToDto(Client client);

    FriendDto fromClientToFriendDto(Client client);
}
