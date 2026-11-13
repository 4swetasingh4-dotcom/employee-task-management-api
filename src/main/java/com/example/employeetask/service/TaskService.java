package com.example.employeetask.service;

import com.example.employeetask.dto.request.TaskRequestDto;
import com.example.employeetask.dto.response.TaskResponseDto;
import com.example.employeetask.entity.Employee;
import com.example.employeetask.entity.Task;
import com.example.employeetask.entity.enums.TaskPriority;
import com.example.employeetask.entity.enums.TaskStatus;
import com.example.employeetask.exception.ResourceNotFoundException;
import com.example.employeetask.repository.EmployeeRepository;
import com.example.employeetask.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public TaskResponseDto createTask(TaskRequestDto requestDto) {
        Employee employee = null;
        if (requestDto.getEmployeeId() != null) {
            employee = employeeRepository.findById(requestDto.getEmployeeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cannot assign task. Employee not found with id: " + requestDto.getEmployeeId()));
        }

        Task task = Task.builder()
                .title(requestDto.getTitle().trim())
                .description(requestDto.getDescription())
                .status(requestDto.getStatus() != null ? requestDto.getStatus() : TaskStatus.PENDING)
                .priority(requestDto.getPriority() != null ? requestDto.getPriority() : TaskPriority.MEDIUM)
                .dueDate(requestDto.getDueDate())
                .assignedEmployee(employee)
                .build();

        Task saved = taskRepository.save(task);
        return mapToDto(saved);
    }

    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll().stream().map(this::mapToDto).toList();
    }

    public TaskResponseDto getTaskById(Long id) {
        Task task = findEntityById(id);
        return mapToDto(task);
    }

    public List<TaskResponseDto> getTasksByEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with id: " + employeeId);
        }
        return taskRepository.findByAssignedEmployeeId(employeeId).stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<TaskResponseDto> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status).stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<TaskResponseDto> getTasksByPriority(TaskPriority priority) {
        return taskRepository.findByPriority(priority).stream()
                .map(this::mapToDto)
                .toList();
    }

    @Transactional
    public TaskResponseDto updateTaskStatus(Long taskId, TaskStatus newStatus) {
        Task task = findEntityById(taskId);
        task.setStatus(newStatus);
        Task updated = taskRepository.save(task);
        return mapToDto(updated);
    }

    @Transactional
    public TaskResponseDto assignTaskToEmployee(Long taskId, Long employeeId) {
        Task task = findEntityById(taskId);
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + employeeId));

        task.setAssignedEmployee(employee);
        Task updated = taskRepository.save(task);
        return mapToDto(updated);
    }

    @Transactional
    public void deleteTask(Long id) {
        Task task = findEntityById(id);
        taskRepository.delete(task);
    }

    public Task findEntityById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));
    }

    public TaskResponseDto mapToDto(Task task) {
        return TaskResponseDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .assignedEmployeeId(task.getAssignedEmployee() != null ? task.getAssignedEmployee().getId() : null)
                .assignedEmployeeName(task.getAssignedEmployee() != null ?
                        task.getAssignedEmployee().getFirstName() + " " + task.getAssignedEmployee().getLastName() : "Unassigned")
                .build();
    }
}

