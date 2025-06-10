package movwe.services;

import lombok.AllArgsConstructor;
import movwe.domains.employees.Employee;
import movwe.domains.employees.dtos.CreateEmployeeDto;
import movwe.repositories.EmployeeRepository;
import movwe.utils.interfaces.DtoInterface;
import movwe.utils.interfaces.ServiceInterface;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService implements ServiceInterface<Employee> {
    private EmployeeRepository employeeRepository;

    @Override
    public Employee get(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    @Override
    public List<Employee> getAll() {
        return new ArrayList<>(employeeRepository.findAll());
    }

    @Override
    public boolean add(DtoInterface dto) {
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
        employeeRepository.deleteAll();
        return true;
    }
}
