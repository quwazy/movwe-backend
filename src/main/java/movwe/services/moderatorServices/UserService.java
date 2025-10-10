package movwe.services.moderatorServices;

import lombok.AllArgsConstructor;
import movwe.domains.users.dtos.UpdateUserDto;
import movwe.domains.users.dtos.UserDto;
import movwe.domains.users.dtos.CreateUserDto;
import movwe.domains.users.User;
import movwe.domains.users.UserMapper;
import movwe.repositories.UserRepository;
import movwe.utils.exceptions.IdNotFoundException;
import movwe.utils.exceptions.UsernameNotFoundException;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements ServiceInterface<UserDto> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper.INSTANCE::fromUserToDto)
                .orElseThrow(() -> new IdNotFoundException("User", id));
    }

    @Transactional(readOnly = true)
    public UserDto getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserMapper.INSTANCE::fromUserToDto)
                .orElseThrow(() -> new UsernameNotFoundException(email));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper.INSTANCE::fromUserToDto)
                .toList();
    }

    @Override
    public UserDto create(DtoInterface dto){
        if (dto instanceof CreateUserDto createUserDto){
            User user = UserMapper.INSTANCE.fromCreateDtoToUser(createUserDto);
            user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
            return UserMapper.INSTANCE.fromUserToDto(userRepository.save(user));
        }
        return null;
    }

    @Override
    public UserDto update(DtoInterface dto) {
        if (dto instanceof UpdateUserDto updateUserDto){
            User user = userRepository.findById(updateUserDto.getId()).orElseThrow(() -> new IdNotFoundException("User", updateUserDto.getId()));
            UserMapper.INSTANCE.fromUpdateDtoToUser(updateUserDto, user);
            if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().isEmpty()){
                user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
            }
            return UserMapper.INSTANCE.fromUserToDto(userRepository.save(user));
        }
        return null;
    }

    @CacheEvict(value = "userByEmail", allEntries = true)
    public UserDto updateActivity(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IdNotFoundException("User", id));
        user.setActive(!user.isActive());
        return UserMapper.INSTANCE.fromUserToDto(userRepository.save(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "userByEmail", allEntries = true)
    public boolean deleteById(Long id) {
        return userRepository.deleteByIdCustom(id) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(value = "userByEmail", allEntries = true)
    public void deleteAll() {
        userRepository.deleteAll();
    }
}
