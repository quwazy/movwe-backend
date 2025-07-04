package movwe.services;

import lombok.AllArgsConstructor;
import movwe.domains.employees.entities.Employee;
import movwe.domains.employees.dtos.CreateEmployeeDto;
import movwe.domains.employees.dtos.EmployeeDto;
import movwe.domains.employees.enums.Role;
import movwe.domains.employees.mappers.EmployeeMapper;
import movwe.repositories.EmployeeRepository;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService implements ServiceInterface<Employee> {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    @Cacheable(value = "employee", key = "#email", unless = "#result == null")
    public Employee getByEmail(String email) {
        return employeeRepository.findByEmail(email).orElse(null);
    }

    @Override
    @Cacheable(value = "employees")
    public List<EmployeeDto> getAll() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper.INSTANCE::fromEmployeeToDto)
                .toList();
    }

    @Override
    @CacheEvict(value = "employees", allEntries = true)
    @CachePut(value = "employee", key = "#result.email", unless = "#result == null")
    public Employee create(DtoInterface dto){
        if (dto instanceof CreateEmployeeDto createEmployeeDto){
            Employee employee = EmployeeMapper.INSTANCE.fromDtoToEmployee(createEmployeeDto);
            employee.setRole(Role.valueOf(createEmployeeDto.getRole()));
            if (employee.getRole().equals(Role.ADMIN)) {
                return null;
            }
            employee.setPassword(passwordEncoder.encode(createEmployeeDto.getPassword()));
            return employeeRepository.save(employee);
        }
        return null;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "employee", key = "#result.email"),
            @CacheEvict(value = "employees", allEntries = true)
    })
    @CachePut(value = "employee", key = "#result.email", unless = "#result == null")
    public Employee update(DtoInterface dto) {
        return null;
    }

    @Caching(evict = {
            @CacheEvict(value = "employee", key = "#email"),
            @CacheEvict(value = "employees", allEntries = true),
            @CacheEvict(value = "userByEmail", key = "#email")
    })
    @CachePut(value = "employee", key = "#result.email", unless = "#result == null")
    public Employee updateActivity(String email) {
        return employeeRepository.findByEmail(email)
                .map(employee -> {
                    employee.setActive(!employee.isActive());
                    return employeeRepository.save(employee);
                })
                .orElse(null);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "employee", allEntries = true),
            @CacheEvict(value = "employees", allEntries = true),
            @CacheEvict(value = "userByEmail", allEntries = true)
    })
    public boolean deleteById(Long id) {
        return employeeRepository.deleteByIdCustom(id) == 1;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "employee", key = "#email"),
            @CacheEvict(value = "employees", allEntries = true),
            @CacheEvict(value = "userByEmail", key = "#email")
    })
    public boolean deleteByEmail(String email){
        return employeeRepository.deleteByEmail(email) == 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "employee", allEntries = true),
            @CacheEvict(value = "employees", allEntries = true),
            @CacheEvict(value = "userByEmail", allEntries = true)
    })
    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
