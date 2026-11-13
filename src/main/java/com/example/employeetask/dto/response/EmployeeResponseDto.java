package com.example.employeetask.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String fullName;
    private String email;
    private String designation;
    private Double salary;
    private Long departmentId;
    private String departmentName;
    private int assignedTasksCount;
}

