package com.jiang.tasks.repository;

import com.jiang.tasks.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends PagingAndSortingRepository<Task, String>, JpaSpecificationExecutor<Task> {
    Optional<Task> findById(Long id);


    void deleteById(Long id);

}
