package com.example.employeetask.controller;

import com.example.employeetask.dto.request.TaskRequestDto;
import com.example.employeetask.dto.request.TaskStatusUpdateDto;
import com.example.employeetask.dto.response.TaskResponseDto;
import com.example.employeetask.entity.enums.TaskPriority;
import com.example.employeetask.entity.enums.TaskStatus;
import com.example.employeetask.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Tasks", description = "Endpoints for creating, assigning, and tracking tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Create a new task (optionally assigned to an employee)")
    public ResponseEntity<TaskResponseDto> createTask(@Valid @RequestBody TaskRequestDto requestDto) {
        TaskResponseDto created = taskService.createTask(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all tasks")
    public ResponseEntity<List<TaskResponseDto>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @GetMapping("/employee/{employeeId}")
    @Operation(summary = "Get all tasks assigned to a specific employee")
    public ResponseEntity<List<TaskResponseDto>> getTasksByEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(taskService.getTasksByEmployee(employeeId));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Filter tasks by status (PENDING, IN_PROGRESS, COMPLETED)")
    public ResponseEntity<List<TaskResponseDto>> getTasksByStatus(@PathVariable TaskStatus status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }

    @GetMapping("/priority/{priority}")
    @Operation(summary = "Filter tasks by priority (LOW, MEDIUM, HIGH)")
    public ResponseEntity<List<TaskResponseDto>> getTasksByPriority(@PathVariable TaskPriority priority) {
        return ResponseEntity.ok(taskService.getTasksByPriority(priority));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update task status")
    public ResponseEntity<TaskResponseDto> updateTaskStatus(
            @PathVariable Long id,
            @Valid @RequestBody TaskStatusUpdateDto statusDto) {
        return ResponseEntity.ok(taskService.updateTaskStatus(id, statusDto.getStatus()));
    }

    @PutMapping("/{id}/assign/{employeeId}")
    @Operation(summary = "Assign or reassign task to an employee")
    public ResponseEntity<TaskResponseDto> assignTask(
            @PathVariable Long id,
            @PathVariable Long employeeId) {
        return ResponseEntity.ok(taskService.assignTaskToEmployee(id, employeeId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task by ID")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}

