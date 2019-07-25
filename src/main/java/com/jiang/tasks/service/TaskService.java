package com.jiang.tasks.service;

import com.jiang.tasks.domain.Task;
import com.jiang.tasks.dto.Status;
import com.jiang.tasks.dto.TaskCreateDto;
import com.jiang.tasks.dto.TaskUpdateDto;
import com.jiang.tasks.repository.TaskRepository;
import com.jiang.tasks.repository.TaskRepositorySpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        return taskRepository.findAll(TaskRepositorySpec.getSpec(requestParams));
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }


    public Task save(TaskCreateDto taskCreateDto) throws ParseException {
        Task newTask = new Task();
        newTask.setStatus(Status.CREATED.getStatus());
        newTask.setTitle(taskCreateDto.getTitle());
        newTask.setDueDate(new SimpleDateFormat("ddMMyyyy").parse(taskCreateDto.getDueDate()));
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
                        try {
                            task.setDueDate(new SimpleDateFormat("ddMMyyyy").parse(newTask.getDueDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (newTask.getStatus() != null) {
                        task.setStatus(newTask.getStatus().getStatus());
                    }
                    return Optional.of(taskRepository.save(task));
                })
                .orElse(null);
    }
}
