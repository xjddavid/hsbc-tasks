package com.jiang.tasks.service;

import com.jiang.tasks.Constants;
import com.jiang.tasks.domain.Task;
import com.jiang.tasks.dto.*;
import com.jiang.tasks.exceptions.EmptyPatchParamException;
import com.jiang.tasks.exceptions.StatusException;
import com.jiang.tasks.exceptions.TaskNotFoundException;
import com.jiang.tasks.exceptions.TitleException;
import com.jiang.tasks.repository.TaskRepository;
import com.jiang.tasks.repository.TaskRepositorySpec;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }


    public List<TaskReturnDto> findAll(Map<String, String> requestParams) {
        TaskQueryDto taskQueryDto = TaskQueryDto.convertToTaskQueryDto(requestParams);
        return taskRepository.findAll(TaskRepositorySpec.getSpec(taskQueryDto))
                .stream().map(TaskReturnDto::convertTaskToReturnDto).collect(Collectors.toList());
    }

    public TaskReturnDto findById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        return TaskReturnDto.convertTaskToReturnDto(task);
    }


    public TaskReturnDto save(TaskCreateDto taskCreateDto) {
        Task newTask = new Task();
        newTask.setStatus(Status.CREATED.getStatus());
        if (Constants.isEmpty(taskCreateDto.getTitle())) {
            throw new TitleException();
        }
        newTask.setTitle(taskCreateDto.getTitle());
        newTask.setDueDate(Constants.convertStringToDate(taskCreateDto.getDueDate()));
        Task task = taskRepository.save(newTask);
        return TaskReturnDto.convertTaskToReturnDto(task);
    }

    @Transactional
    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

    public TaskReturnDto update(TaskUpdateDto newTask, Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    if (Constants.isEmpty(newTask.getTitle())) {
                        throw new TitleException();
                    }
                    task.setTitle(newTask.getTitle());
                    task.setDueDate(Constants.convertStringToDate(newTask.getDueDate()));
                    task.setStatus(newTask.getStatus().getStatus());
                    Task updatedTask = taskRepository.save(task);
                    return TaskReturnDto.convertTaskToReturnDto(updatedTask);

                })
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public TaskReturnDto pacth(Map<String, String> update, Long id) {
        if (update.size() == 0) {
            throw new EmptyPatchParamException();
        }
        return taskRepository.findById(id)
                .map(task -> {
                    if (update.get("title") != null) {
                        if (Constants.isEmpty(update.get("title"))) {
                            throw new TitleException();
                        }
                        task.setTitle(update.get("title"));
                    }
                    if (update.get("dueDate") != null) {
                        task.setDueDate(Constants.convertStringToDate(update.get("dueDate")));
                    }
                    if (update.get("status") != null) {
                        if (!Status.contain(update.get("status"))) {
                            throw new StatusException(update.get("status"));
                        }
                        task.setStatus(update.get("status"));
                    }
                    Task updatedTask = taskRepository.save(task);
                    return TaskReturnDto.convertTaskToReturnDto(updatedTask);
                })
                .orElseThrow(() -> new TaskNotFoundException(id));
    }
}
