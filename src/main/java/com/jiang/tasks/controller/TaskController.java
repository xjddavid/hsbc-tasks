package com.jiang.tasks.controller;

import com.jiang.tasks.domain.Task;
import com.jiang.tasks.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
    public String getTask(@RequestParam(value = "id", required = false) Long id) {
        Task task = taskRepository.findById(id);
        return task.getTitle();
    }
}
