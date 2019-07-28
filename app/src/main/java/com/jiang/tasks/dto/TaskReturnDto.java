package com.jiang.tasks.dto;

import com.jiang.tasks.Constants;
import com.jiang.tasks.domain.Task;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TaskReturnDto {
    private Long id;
    private String title;
    private String dueDate;
    private String status;

    public static TaskReturnDto convertTaskToReturnDto(Task task) {
        TaskReturnDto taskReturnDto = new TaskReturnDto();
        taskReturnDto.setId(task.getId());
        taskReturnDto.setStatus(task.getStatus());
        taskReturnDto.setTitle(task.getTitle());
        taskReturnDto.setDueDate(Constants.convertDateToString(task.getDueDate()));
        return taskReturnDto;
    }
}
