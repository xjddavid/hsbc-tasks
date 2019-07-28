package com.jiang.tasks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskCreateDto {
    private String title;
    private String dueDate;
}

