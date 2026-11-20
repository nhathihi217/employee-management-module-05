package com.example.employeemanagement.employee;

import com.example.employeemanagement.department.Department;
import com.example.employeemanagement.department.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Employee> findAll() { return employeeRepository.findAll(); }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Employee create(EmployeeRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.departmentId()));
        return employeeRepository.save(new Employee(request.name(), request.email(), department));
    }

    public Employee update(Long id, EmployeeRequest request) {
        Employee employee = findById(id);
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + request.departmentId()));
        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {
        Employee employee = findById(id);
        employeeRepository.delete(employee);
    }

    public List<Employee> search(String name, String department) {
        if (name != null && !name.isBlank()) {
            return employeeRepository.findByNameContainingIgnoreCase(name.trim());
        }
        if (department != null && !department.isBlank()) {
            return employeeRepository.findByDepartmentNameContainingIgnoreCase(department.trim());
        }
        return employeeRepository.findAll();
    }
}
