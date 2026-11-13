package com.example.employeetask.controller;

import com.example.employeetask.dto.request.DepartmentRequestDto;
import com.example.employeetask.dto.response.DepartmentResponseDto;
import com.example.employeetask.service.DepartmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
@Tag(name = "Departments", description = "Endpoints for managing organization departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @PostMapping
    @Operation(summary = "Create a new department")
    public ResponseEntity<DepartmentResponseDto> createDepartment(@Valid @RequestBody DepartmentRequestDto requestDto) {
        DepartmentResponseDto created = departmentService.createDepartment(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all departments")
    public ResponseEntity<List<DepartmentResponseDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get department by ID")
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing department")
    public ResponseEntity<DepartmentResponseDto> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequestDto requestDto) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete department by ID")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }
}

