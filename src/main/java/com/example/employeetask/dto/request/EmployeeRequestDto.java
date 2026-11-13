package com.example.employeetask.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeRequestDto {

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Designation cannot be blank")
    private String designation;

    @Positive(message = "Salary must be greater than zero")
    private Double salary;

    @NotNull(message = "Department ID is required")
    private Long departmentId;
}

