package com.example.employeetask.repository;

import com.example.employeetask.entity.Task;
import com.example.employeetask.entity.enums.TaskPriority;
import com.example.employeetask.entity.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedEmployeeId(Long employeeId);
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByPriority(TaskPriority priority);
}

