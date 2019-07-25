package com.jiang.tasks.controller;

import com.jiang.tasks.domain.Task;
import com.jiang.tasks.repository.TaskRepository;
import com.jiang.tasks.result.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
