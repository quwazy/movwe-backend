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

    @GetMapping(path = "/get/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        if (employeeService.get(id) != null){
            return ResponseEntity.ok(employeeService.get(id));
        }
        return ResponseEntity.badRequest().body("Employee with id " + id + " does not exist");
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAll());
    }

    @PostMapping(path = "/add")
    public ResponseEntity<?> addEmployee(@RequestBody CreateEmployeeDto createEmployeeDto) {
        try
        {
            if (employeeService.add(createEmployeeDto)){
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.badRequest().build();
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

//    @PutMapping(path = "/update/{id}")
//    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody CreateEmployeeDto createEmployeeDto) {
//        try {
//            if (employeeService.update(id, createEmployeeDto)) {
//                return ResponseEntity.ok().build();
//            }
//            else {
//                return ResponseEntity.badRequest().build();
//            }
//        }
//        catch (Exception ex){
//            return ResponseEntity.badRequest().body(ex.getMessage());
//        }
//    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        try {
            if (employeeService.delete(id)) {
                return ResponseEntity.ok().build();
            }
            else {
                return ResponseEntity.badRequest().build();
            }
        }
        catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @DeleteMapping(path = "/deleteAll")
    public ResponseEntity<?> deleteAllEmployees() {
        try {
            if (employeeService.deleteAll())
            {
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
