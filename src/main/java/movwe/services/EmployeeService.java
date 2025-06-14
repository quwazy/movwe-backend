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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService implements ServiceInterface {
    private EmployeeRepository employeeRepository;

    @Override
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
    public boolean add(DtoInterface dto) {
        if (dto instanceof CreateEmployeeDto createEmployeeDto) {
            Employee employee = EmployeeMapper.INSTANCE.fromDtoToEmployee(createEmployeeDto);
            employee.setRole(Role.valueOf(createEmployeeDto.getRole()));

            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Long id, DtoInterface dto) {
        return false;
    }

    @Override
    public boolean delete(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteAll() {
        if (employeeRepository.count() != 0) {
            employeeRepository.deleteAll();
            return true;
        }
        return false;
    }
}
