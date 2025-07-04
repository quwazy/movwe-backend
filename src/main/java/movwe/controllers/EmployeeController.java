package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import movwe.domains.employees.entities.Employee;
import movwe.domains.employees.mappers.EmployeeMapper;
import movwe.services.EmployeeService;
import movwe.utils.interfaces.ControllerInterface;
import movwe.utils.interfaces.DtoInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "/api/employees")
public class EmployeeController implements ControllerInterface {
    private final EmployeeService employeeService;

    @Operation(summary = "Changing employee's active field")
    @PutMapping(path = "/active/{email}")
    public ResponseEntity<?> changeEmployeeActive(@PathVariable String email){
        try {
            if (employeeService.updateActivity(email) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with changing employee active field");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getById(Long id) {
        try {
            Employee employee = employeeService.getById(id);
            if (employee != null){
                return ResponseEntity.ok(EmployeeMapper.INSTANCE.fromEmployeeToDto(employee));
            }
            return ResponseEntity.badRequest().body("Something went wrong with get employee by id " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getByEmail(String email) {
        try {
            Employee employee = employeeService.getByEmail(email);
            if (employee != null){
                return ResponseEntity.ok(EmployeeMapper.INSTANCE.fromEmployeeToDto(employee));
            }
            return ResponseEntity.badRequest().body("Something went wrong with get employee by email " + email);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(employeeService.getAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> create(DtoInterface dto) {
        try {
            if (employeeService.create(dto) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with creating employee");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> update(DtoInterface dto) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        try {
            if (employeeService.deleteById(id)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with deleting employee");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteByEmail(String email) {
        try {
            if (employeeService.deleteByEmail(email)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().body("Something went wrong with deleting employee");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAll() {
        try {
            employeeService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
