package com.jiang.tasks.controller;

import com.jiang.tasks.domain.Task;
import com.jiang.tasks.dto.TaskCreateDto;
import com.jiang.tasks.dto.TaskUpdateDto;
import com.jiang.tasks.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping("/")
    @ResponseBody
    public String home() {
        return "hello world";
    }

    @GetMapping("/tasks")
    List<Task> all(@RequestParam Map<String, String> requestParams) {

        return taskService.findAll(requestParams);
    }

    @PostMapping("/tasks")
    Task newTask(@RequestBody TaskCreateDto taskCreateDto) {
        return taskService.save(taskCreateDto);
    }

    @GetMapping("/tasks/{id}")
    Task one(@PathVariable Long id) {
        return taskService.findById(id);
    }

    @PutMapping("/tasks/{id}")
    Task replaceTask(@RequestBody TaskUpdateDto newTask, @PathVariable Long id) {
        return taskService.update(newTask, id);
    }

    @DeleteMapping("/tasks/{id}")
    void deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
