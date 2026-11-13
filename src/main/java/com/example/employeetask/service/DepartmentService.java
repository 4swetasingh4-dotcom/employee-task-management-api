package com.example.employeetask.service;

import com.example.employeetask.dto.request.DepartmentRequestDto;
import com.example.employeetask.dto.response.DepartmentResponseDto;
import com.example.employeetask.entity.Department;
import com.example.employeetask.exception.DuplicateResourceException;
import com.example.employeetask.exception.ResourceNotFoundException;
import com.example.employeetask.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Transactional
    public DepartmentResponseDto createDepartment(DepartmentRequestDto requestDto) {
        if (departmentRepository.existsByNameIgnoreCase(requestDto.getName())) {
            throw new DuplicateResourceException("Department with name '" + requestDto.getName() + "' already exists.");
        }

        Department department = Department.builder()
                .name(requestDto.getName().trim())
                .location(requestDto.getLocation())
                .build();

        Department saved = departmentRepository.save(department);
        return mapToDto(saved);
    }

    public List<DepartmentResponseDto> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::mapToDto)
                .toList();
    }

    public DepartmentResponseDto getDepartmentById(Long id) {
        Department dept = findEntityById(id);
        return mapToDto(dept);
    }

    @Transactional
    public DepartmentResponseDto updateDepartment(Long id, DepartmentRequestDto requestDto) {
        Department dept = findEntityById(id);

        if (!dept.getName().equalsIgnoreCase(requestDto.getName())
                && departmentRepository.existsByNameIgnoreCase(requestDto.getName())) {
            throw new DuplicateResourceException("Department name '" + requestDto.getName() + "' is already in use.");
        }

        dept.setName(requestDto.getName().trim());
        dept.setLocation(requestDto.getLocation());

        Department updated = departmentRepository.save(dept);
        return mapToDto(updated);
    }

    @Transactional
    public void deleteDepartment(Long id) {
        Department dept = findEntityById(id);
        departmentRepository.delete(dept);
    }

    public Department findEntityById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    public DepartmentResponseDto mapToDto(Department dept) {
        return DepartmentResponseDto.builder()
                .id(dept.getId())
                .name(dept.getName())
                .location(dept.getLocation())
                .totalEmployees(dept.getEmployees() != null ? dept.getEmployees().size() : 0)
                .build();
    }
}

