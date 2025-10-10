package movwe.services.moderatorServices;

import lombok.AllArgsConstructor;
import movwe.domains.moderators.Moderator;
import movwe.domains.moderators.dtos.CreateModeratorDto;
import movwe.domains.moderators.dtos.ModeratorDto;
import movwe.domains.moderators.dtos.UpdateModeratorDto;
import movwe.domains.moderators.enums.Role;
import movwe.domains.moderators.ModeratorMapper;
import movwe.repositories.ModeratorRepository;
import movwe.utils.exceptions.IdNotFoundException;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class ModeratorService implements ServiceInterface<ModeratorDto> {
    private final ModeratorRepository moderatorRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public ModeratorDto getById(Long id) {
        return moderatorRepository.findByIdCustom(id)
                .map(ModeratorMapper.INSTANCE::fromModeratorToDto)
                .orElseThrow(() -> new IdNotFoundException("Moderator", id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ModeratorDto> getAll() {
        return moderatorRepository.findAllCustom()
                .orElseGet(Collections::emptyList)
                .stream()
                .map(ModeratorMapper.INSTANCE::fromModeratorToDto)
                .toList();
    }

    @Override
    public ModeratorDto create(DtoInterface dto){
        if (dto instanceof CreateModeratorDto createModeratorDto){
            Moderator moderator = ModeratorMapper.INSTANCE.fromCreteDtoToModerator(createModeratorDto);
            moderator.setPassword(passwordEncoder.encode(createModeratorDto.getPassword()));
            moderator.setRole(Role.EDITOR);
            return ModeratorMapper.INSTANCE.fromModeratorToDto(moderatorRepository.save(moderator));
        }
        return null;
    }

    @Override
    public ModeratorDto update(DtoInterface dto) {
        if (dto instanceof UpdateModeratorDto updateModeratorDto){
            Moderator moderator = moderatorRepository.findByIdCustom(updateModeratorDto.getId()).orElseThrow(() -> new IdNotFoundException("Moderator", updateModeratorDto.getId()));
            ModeratorMapper.INSTANCE.fromUpdateDtoToModerator(updateModeratorDto, moderator);
            if (updateModeratorDto.getPassword() != null && !updateModeratorDto.getPassword().isEmpty()) {
                moderator.setPassword(passwordEncoder.encode(updateModeratorDto.getPassword()));
            }
            return ModeratorMapper.INSTANCE.fromModeratorToDto(moderatorRepository.save(moderator));
        }
        return null;
    }

    @CacheEvict(value = "userByEmail", allEntries = true)
    public ModeratorDto updateActivity(Long id) {
        Moderator moderator = moderatorRepository.findByIdCustom(id).orElseThrow(() -> new IdNotFoundException("Moderator", id));
        moderator.setActive(!moderator.isActive());
        return ModeratorMapper.INSTANCE.fromModeratorToDto(moderatorRepository.save(moderator));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "userByEmail", allEntries = true)
    public boolean deleteById(Long id) {
        return moderatorRepository.deleteByIdCustom(id) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "userByEmail", allEntries = true)
    public void deleteAll() {
        moderatorRepository.deleteAllCustom();
    }
}
