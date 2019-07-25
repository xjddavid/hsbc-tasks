package com.jiang.tasks.controller;

import com.jiang.tasks.TaskNotFoundException;
import com.jiang.tasks.domain.Task;
import com.jiang.tasks.dto.TaskCreateDto;
import com.jiang.tasks.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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

//    @RequestMapping("/tasks")
//    @ResponseBody
//    public Result<Task> getTask(@RequestParam(value = "id", required = false) Long id) {
//        Optional<Task> task = taskRepository.findById(id);
//        return task.map(Result::success).orElse(Result.error(QUERY_ERROR));
//    }

    @GetMapping("/tasks")
    List<Task> all(@RequestParam Map<String, String> requestParams) {

        return taskService.findAll(requestParams);
    }

    @PostMapping("/tasks")
    Task newTask(@RequestBody TaskCreateDto taskCreateDto) throws ParseException {
        return taskService.save(taskCreateDto);
    }

    @GetMapping("/tasks/{id}")
    Task one(@PathVariable Long id) {
        return taskService.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

//    @PutMapping("/tasks/{id}")
//    Task replaceTask(@RequestBody Task newTask, @PathVariable Long id) {
//        return taskRepository.findById(id)
//                .map(task -> {
//                    task.setTitle(newTask.getTitle());
//                    task.setDueDate(newTask.getDueDate());
//                    task.setStatus(newTask.getStatus());
//                    return taskRepository.save(task);
//                })
//                .orElseGet(() -> {
//                    newTask.setId(id);
//                    return taskRepository.save(newTask);
//                });
//    }

    @DeleteMapping("/tasks/{id}")
    void deleteTask(@PathVariable Long id) {
        taskService.deleteById(id);
    }
}
