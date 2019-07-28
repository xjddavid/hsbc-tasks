package com.jiang.tasks.dto;

import lombok.Data;

@Data
public class TaskUpdateDto {
    private String title;
    private String dueDate;
    private Status status;
}
