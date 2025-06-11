package movwe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import movwe.domains.employees.dtos.CreateEmployeeDto;
import movwe.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping(path = "/get/{id}")
    @Operation(summary = "Get employee by id")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        try {
            if (id == null) {
                return ResponseEntity.badRequest().build();
            }
            if (employeeService.get(id) != null){
                return ResponseEntity.ok(employeeService.get(id));
            }
            return ResponseEntity.badRequest().body("Employee with id " + id + " does not exist");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/getAll")
    @Operation(summary = "Get all employees")
    public ResponseEntity<?> getAllEmployees() {
        try {
            return ResponseEntity.ok(employeeService.getAll());
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping(path = "/add")
    @Operation(summary = "Add new employee")
    public ResponseEntity<?> addEmployee(@RequestBody CreateEmployeeDto createEmployeeDto) {
        try {
            if (employeeService.add(createEmployeeDto)){
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
            if (employeeService.delete(id)) {
                return ResponseEntity.ok().build();
            }
            else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping(path = "/deleteAll")
    @Operation(summary = "Delete all employees in table")
    public ResponseEntity<?> deleteAllEmployees() {
        try {
            if (employeeService.deleteAll()) {
                return ResponseEntity.ok().build();
            }
            else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
