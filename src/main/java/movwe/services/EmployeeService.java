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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService implements ServiceInterface<Employee> {
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
    public Employee add(DtoInterface dto) {
        if (dto instanceof CreateEmployeeDto createEmployeeDto) {
            Employee employee = EmployeeMapper.INSTANCE.fromDtoToEmployee(createEmployeeDto);
            employee.setRole(Role.valueOf(createEmployeeDto.getRole()));

            return employeeRepository.save(employee);
        }
        return null;
    }

    @Override
    public Employee update(Long id, DtoInterface dto) {
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
    @CacheEvict(value = "employees", allEntries = true)
    public void deleteAll() {
        employeeRepository.deleteAll();
    }
}
