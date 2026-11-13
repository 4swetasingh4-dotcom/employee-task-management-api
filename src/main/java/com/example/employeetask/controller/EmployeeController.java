package com.example.employeetask.controller;

import com.example.employeetask.dto.request.EmployeeRequestDto;
import com.example.employeetask.dto.response.EmployeeResponseDto;
import com.example.employeetask.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Tag(name = "Employees", description = "Endpoints for managing employee profiles and records")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @Operation(summary = "Register a new employee")
    public ResponseEntity<EmployeeResponseDto> createEmployee(@Valid @RequestBody EmployeeRequestDto requestDto) {
        EmployeeResponseDto created = employeeService.createEmployee(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get employees with pagination and sorting")
    public ResponseEntity<Page<EmployeeResponseDto>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        Sort sort = direction.equalsIgnoreCase("desc") ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        PageRequest pageRequest = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(employeeService.getAllEmployees(pageRequest));
    }

    @GetMapping("/all")
    @Operation(summary = "Get all employees as a simple list")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployeesList() {
        return ResponseEntity.ok(employeeService.getAllEmployeesList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee details by ID")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/department/{departmentId}")
    @Operation(summary = "Get all employees in a specific department")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByDepartment(@PathVariable Long departmentId) {
        return ResponseEntity.ok(employeeService.getEmployeesByDepartment(departmentId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee profile")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDto requestDto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee by ID")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}

