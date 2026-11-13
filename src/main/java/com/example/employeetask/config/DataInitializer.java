package com.example.employeetask.config;

import com.example.employeetask.entity.Department;
import com.example.employeetask.entity.Employee;
import com.example.employeetask.entity.Task;
import com.example.employeetask.entity.enums.TaskPriority;
import com.example.employeetask.entity.enums.TaskStatus;
import com.example.employeetask.repository.DepartmentRepository;
import com.example.employeetask.repository.EmployeeRepository;
import com.example.employeetask.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final TaskRepository taskRepository;

    @Override
    public void run(String... args) {
        if (departmentRepository.count() > 0) {
            log.info("Database already seeded with initial data.");
            return;
        }

        log.info("Seeding initial sample data for Departments, Employees, and Tasks...");

        // 1. Seed Departments
        Department engineering = Department.builder()
                .name("Engineering")
                .location("Building A, 4th Floor")
                .build();

        Department hr = Department.builder()
                .name("Human Resources")
                .location("Building B, 2nd Floor")
                .build();

        Department marketing = Department.builder()
                .name("Marketing")
                .location("Building C, 1st Floor")
                .build();

        departmentRepository.saveAll(List.of(engineering, hr, marketing));

        // 2. Seed Employees
        Employee john = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@company.com")
                .designation("Senior Backend Engineer")
                .salary(85000.0)
                .department(engineering)
                .build();

        Employee jane = Employee.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@company.com")
                .designation("Frontend Developer")
                .salary(72000.0)
                .department(engineering)
                .build();

        Employee alice = Employee.builder()
                .firstName("Alice")
                .lastName("Johnson")
                .email("alice.johnson@company.com")
                .designation("HR Specialist")
                .salary(60000.0)
                .department(hr)
                .build();

        employeeRepository.saveAll(List.of(john, jane, alice));

        // 3. Seed Tasks
        Task task1 = Task.builder()
                .title("Design Database Schema for Payroll")
                .description("Create entity relationships and migrate schema using Hibernate/JPA.")
                .status(TaskStatus.IN_PROGRESS)
                .priority(TaskPriority.HIGH)
                .dueDate(LocalDate.now().plusDays(5))
                .assignedEmployee(john)
                .build();

        Task task2 = Task.builder()
                .title("Implement REST Authentication & JWT")
                .description("Configure Spring Security filter chain and generate JWT access tokens.")
                .status(TaskStatus.PENDING)
                .priority(TaskPriority.HIGH)
                .dueDate(LocalDate.now().plusDays(10))
                .assignedEmployee(john)
                .build();

        Task task3 = Task.builder()
                .title("Build Employee Dashboard UI")
                .description("Develop responsive dashboard tables and charts in React.")
                .status(TaskStatus.IN_PROGRESS)
                .priority(TaskPriority.MEDIUM)
                .dueDate(LocalDate.now().plusDays(7))
                .assignedEmployee(jane)
                .build();

        Task task4 = Task.builder()
                .title("Prepare Quarterly HR Review")
                .description("Consolidate feedback and employee performance scores.")
                .status(TaskStatus.PENDING)
                .priority(TaskPriority.LOW)
                .dueDate(LocalDate.now().plusDays(14))
                .assignedEmployee(alice)
                .build();

        taskRepository.saveAll(List.of(task1, task2, task3, task4));

        log.info("Sample data initialization completed successfully!");
    }
}

