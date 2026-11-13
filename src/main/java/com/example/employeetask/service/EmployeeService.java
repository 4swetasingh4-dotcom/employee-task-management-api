package com.example.employeetask.service;

import com.example.employeetask.dto.request.EmployeeRequestDto;
import com.example.employeetask.dto.response.EmployeeResponseDto;
import com.example.employeetask.entity.Department;
import com.example.employeetask.entity.Employee;
import com.example.employeetask.exception.DuplicateResourceException;
import com.example.employeetask.exception.ResourceNotFoundException;
import com.example.employeetask.repository.DepartmentRepository;
import com.example.employeetask.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
        if (employeeRepository.existsByEmailIgnoreCase(requestDto.getEmail())) {
            throw new DuplicateResourceException("Employee with email '" + requestDto.getEmail() + "' already exists.");
        }

        Department department = departmentRepository.findById(requestDto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + requestDto.getDepartmentId()));

        Employee employee = Employee.builder()
                .firstName(requestDto.getFirstName().trim())
                .lastName(requestDto.getLastName().trim())
                .email(requestDto.getEmail().trim().toLowerCase())
                .designation(requestDto.getDesignation().trim())
                .salary(requestDto.getSalary())
                .department(department)
                .build();

        Employee saved = employeeRepository.save(employee);
        return mapToDto(saved);
    }

    public Page<EmployeeResponseDto> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable).map(this::mapToDto);
    }

    public List<EmployeeResponseDto> getAllEmployeesList() {
        return employeeRepository.findAll().stream().map(this::mapToDto).toList();
    }

    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = findEntityById(id);
        return mapToDto(employee);
    }

    public List<EmployeeResponseDto> getEmployeesByDepartment(Long departmentId) {
        if (!departmentRepository.existsById(departmentId)) {
            throw new ResourceNotFoundException("Department not found with id: " + departmentId);
        }
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional
    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto) {
        Employee employee = findEntityById(id);

        if (!employee.getEmail().equalsIgnoreCase(requestDto.getEmail())
                && employeeRepository.existsByEmailIgnoreCase(requestDto.getEmail())) {
            throw new DuplicateResourceException("Email '" + requestDto.getEmail() + "' is already registered to another employee.");
        }

        Department department = departmentRepository.findById(requestDto.getDepartmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + requestDto.getDepartmentId()));

        employee.setFirstName(requestDto.getFirstName().trim());
        employee.setLastName(requestDto.getLastName().trim());
        employee.setEmail(requestDto.getEmail().trim().toLowerCase());
        employee.setDesignation(requestDto.getDesignation().trim());
        employee.setSalary(requestDto.getSalary());
        employee.setDepartment(department);

        Employee updated = employeeRepository.save(employee);
        return mapToDto(updated);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        Employee employee = findEntityById(id);
        employeeRepository.delete(employee);
    }

    public Employee findEntityById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    public EmployeeResponseDto mapToDto(Employee employee) {
        return EmployeeResponseDto.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .fullName(employee.getFirstName() + " " + employee.getLastName())
                .email(employee.getEmail())
                .designation(employee.getDesignation())
                .salary(employee.getSalary())
                .departmentId(employee.getDepartment() != null ? employee.getDepartment().getId() : null)
                .departmentName(employee.getDepartment() != null ? employee.getDepartment().getName() : null)
                .assignedTasksCount(employee.getTasks() != null ? employee.getTasks().size() : 0)
                .build();
    }
}

