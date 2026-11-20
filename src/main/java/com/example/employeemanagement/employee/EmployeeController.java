package com.example.employeemanagement.employee;

import com.example.employeemanagement.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) { this.employeeService = employeeService; }

    @GetMapping
    public List<Employee> getAll(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String department) {
        return employeeService.search(name, department);
    }

    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) { return employeeService.findById(id); }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody EmployeeRequest request,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Request contains invalid employee data", bindingResult);
        }
        Employee employee = employeeService.create(request);
        return ResponseEntity.created(URI.create("/api/employees/" + employee.getId())).body(employee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @Valid @RequestBody EmployeeRequest request,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Request contains invalid employee data", bindingResult);
        }
        return ResponseEntity.ok(employeeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
