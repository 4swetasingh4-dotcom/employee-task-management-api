package com.example.employeetask.dto.request;

import com.example.employeetask.entity.enums.TaskPriority;
import com.example.employeetask.entity.enums.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskRequestDto {

    @NotBlank(message = "Task title is required")
    @Size(min = 3, max = 150, message = "Task title must be between 3 and 150 characters")
    private String title;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    @FutureOrPresent(message = "Due date must be today or in the future")
    private LocalDate dueDate;

    private Long employeeId; // Optional at creation; can assign later
}

