package movwe.services;

import lombok.AllArgsConstructor;
import movwe.domains.employees.Employee;
import movwe.domains.employees.dtos.CreateEmployeeDto;
import movwe.repositories.EmployeeRepository;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService implements ServiceInterface<Employee> {
    private EmployeeRepository employeeRepository;

    @Override
    public Employee get(Long id) {
        return null;
    }

    @Override
    public List<Employee> getAll() {
        return List.of();
    }

    @Override
    public boolean add(DtoInterface dto) {
        return false;
    }

    @Override
    public boolean edit(Long id, DtoInterface dto) {
        if (dto instanceof CreateEmployeeDto) {
            CreateEmployeeDto createEmployeeDto = (CreateEmployeeDto) dto;
            Employee employee =  new Employee();
            employee.setEmail(createEmployeeDto.getEmail());
            employee.setPassword(createEmployeeDto.getPassword());
            employee.setFirstName(createEmployeeDto.getFirstName());
            employee.setLastName(createEmployeeDto.getLastName());

            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public boolean deleteAll() {
        employeeRepository.deleteAll();
        return true;
    }
}
