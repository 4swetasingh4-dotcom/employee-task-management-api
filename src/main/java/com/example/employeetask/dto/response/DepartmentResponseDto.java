package com.example.employeetask.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponseDto {
    private Long id;
    private String name;
    private String location;
    private int totalEmployees;
}

