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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService implements ServiceInterface {
    private EmployeeRepository employeeRepository;

    @Override
    @Cacheable(value = "employee", key = "#id")
    public EmployeeDto get(Long id) {
        return EmployeeMapper.INSTANCE.fromEmployeeToDto(employeeRepository.findById(id).orElse(null));
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
    @CachePut(value = "employee", key = "#result.id", condition = "#result != null")
    public DtoInterface add(DtoInterface dto) {
        if (dto instanceof CreateEmployeeDto createEmployeeDto && createEmployeeDto.getRole() != null && !createEmployeeDto.getRole().equalsIgnoreCase("ADMIN")) {
            Employee employee = EmployeeMapper.INSTANCE.fromDtoToEmployee(createEmployeeDto);
            employee.setRole(Role.valueOf(createEmployeeDto.getRole()));
            return EmployeeMapper.INSTANCE.fromEmployeeToDto(employeeRepository.save(employee));
        }
        return null;
    }

    @Override
    public DtoInterface update(Long id, DtoInterface dto) {
        return null;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "employee", key = "#id"),
            @CacheEvict(value = "employees", allEntries = true)
    })
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @Caching(evict = {
            @CacheEvict(value = "employees", allEntries = true),
            @CacheEvict(value = "employee", allEntries = true)
    })
    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
