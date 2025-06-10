package movwe.controllers;

import lombok.AllArgsConstructor;
import movwe.domains.employees.dtos.CreateEmployeeDto;
import movwe.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/employees")
public class EmployeeController {
    private EmployeeService employeeService;

    @GetMapping(path = "/getAll")
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> addEmployee(@RequestBody CreateEmployeeDto createEmployeeDto) {
        try {
            if (employeeService.add(createEmployeeDto)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }catch (Exception ex){
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
