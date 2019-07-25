package com.jiang.tasks.controller;

import com.jiang.tasks.TaskNotFoundException;
import com.jiang.tasks.domain.Task;
import com.jiang.tasks.repository.TaskRepository;
import com.jiang.tasks.result.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.jiang.tasks.result.CodeMsg.QUERY_ERROR;

@RestController
@RequestMapping("/api")
public class TaskController {
    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "hello world";
    }

    @RequestMapping("/tasks")
    @ResponseBody
    public Result<Task> getTask(@RequestParam(value = "id", required = false) Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(Result::success).orElse(Result.error(QUERY_ERROR));
    }

    @GetMapping("/tasks")
    List<Task> all() {
        return taskRepository.findAll();
    }

    @PostMapping("/tasks")
    Task newTask(@RequestBody Task nT) {
        return taskRepository.save(nT);
    }

    @GetMapping("/tasks/{id}")
    Task one(@PathVariable Long id) {

        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @PutMapping("/tasks/{id}")
    Task replaceTask(@RequestBody Task newTask, @PathVariable Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(newTask.getTitle());
                    task.setDueDate(newTask.getDueDate());
                    task.setStatus(newTask.getStatus());
                    return taskRepository.save(task);
                })
                .orElseGet(() -> {
                    newTask.setId(id);
                    return taskRepository.save(newTask);
                });
    }

    @DeleteMapping("/tasks/{id}")
    void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
}
