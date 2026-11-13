package com.example.employeetask.dto.response;

import com.example.employeetask.entity.enums.TaskPriority;
import com.example.employeetask.entity.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponseDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDate dueDate;
    private Long assignedEmployeeId;
    private String assignedEmployeeName;
}

