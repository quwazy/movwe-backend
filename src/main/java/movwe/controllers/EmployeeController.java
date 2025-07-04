package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import movwe.domains.employees.dtos.CreateEmployeeDto;
import movwe.domains.employees.entities.Employee;
import movwe.domains.employees.mappers.EmployeeMapper;
import movwe.services.EmployeeService;
import movwe.utils.interfaces.DtoInterface;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@AllArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping(path = "/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping(path = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get employee by id")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        try {
            Optional<DtoInterface> employeeDto = employeeService.get(id);
            if (employeeDto.isPresent()) {
                return ResponseEntity.ok(employeeDto.get());
            }
            return ResponseEntity.badRequest().body("Employee with id " + id + " does not exist");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/getByEmail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get employee by email")
    public ResponseEntity<?> getEmployeeByEmail(@PathVariable String email) {
        try {
            Optional<Employee> employeeDto = employeeService.getByEmail(email);
            if (employeeDto.isPresent()) {
                return ResponseEntity.ok(EmployeeMapper.INSTANCE.fromEmployeeToDto(employeeDto.get()));
            }
            return ResponseEntity.badRequest().body("Employee with email " + email + " does not exist");
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get all employees")
    public ResponseEntity<?> getAllEmployees() {
        try {
            return ResponseEntity.ok(employeeService.getAll());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add new employee")
    public ResponseEntity<?> addEmployee(@Valid @RequestBody CreateEmployeeDto createEmployeeDto) {
        try {
            if (employeeService.addEmployee(createEmployeeDto) != null){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    @Operation(summary = "Delete employee by id")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping(path = "/deleteByEmail/{email}")
    @Operation(summary = "Delete employee by email")
    public ResponseEntity<?> deleteEmployeeByEmail(@PathVariable String email) {
        try {
            employeeService.deleteByEmail(email);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping(path = "/deleteAll")
    @Operation(summary = "Delete all employees in table")
    public ResponseEntity<?> deleteAllEmployees() {
        try {
            employeeService.deleteAll();
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
