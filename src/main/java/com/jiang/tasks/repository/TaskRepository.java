package com.jiang.tasks.repository;

import com.jiang.tasks.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findById(Long id);
}
