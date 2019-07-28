package com.jiang.tasks.dto;

import com.jiang.tasks.enums.Status;
import lombok.Data;

@Data
public class TaskUpdateDto {
    private String title;
    private String dueDate;
    private Status status;
}
