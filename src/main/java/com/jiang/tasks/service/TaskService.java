package com.jiang.tasks.service;

import com.jiang.tasks.Constants;
import com.jiang.tasks.domain.Task;
import com.jiang.tasks.dto.Status;
import com.jiang.tasks.dto.TaskCreateDto;
import com.jiang.tasks.dto.TaskQueryDto;
import com.jiang.tasks.dto.TaskUpdateDto;
import com.jiang.tasks.exceptions.TitleException;
import com.jiang.tasks.repository.TaskRepository;
import com.jiang.tasks.repository.TaskRepositorySpec;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<Task> findAll(Map<String, String> requestParams) {
        TaskQueryDto taskQueryDto = TaskQueryDto.convertToTaskQueryDto(requestParams);
        return taskRepository.findAll(TaskRepositorySpec.getSpec(taskQueryDto));
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }


    public Task save(TaskCreateDto taskCreateDto) {
        Task newTask = new Task();
        newTask.setStatus(Status.CREATED.getStatus());
        if (taskCreateDto.getTitle().equals("")) {
            throw new TitleException();
        }
        newTask.setTitle(taskCreateDto.getTitle());
        newTask.setDueDate(Constants.convertStringToDate(taskCreateDto.getDueDate()));
        return taskRepository.save(newTask);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public Optional<Task> update(TaskUpdateDto newTask, Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    if (newTask.getTitle() != null) {
                        task.setTitle(newTask.getTitle());
                    }
                    if (newTask.getDueDate() != null) {
                        task.setDueDate(Constants.convertStringToDate(newTask.getDueDate()));
                    }
                    if (newTask.getStatus() != null) {
                        task.setStatus(newTask.getStatus().getStatus());
                    }
                    return Optional.of(taskRepository.save(task));
                })
                .orElse(null);
    }
}
