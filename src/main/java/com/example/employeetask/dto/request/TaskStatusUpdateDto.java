package com.example.employeetask.dto.request;

import com.example.employeetask.entity.enums.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatusUpdateDto {

    @NotNull(message = "Task status is required (PENDING, IN_PROGRESS, COMPLETED)")
    private TaskStatus status;
}

