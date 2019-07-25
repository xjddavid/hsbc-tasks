package com.jiang.tasks.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskCreateDto {
    private String title;
    private String dueDate;
}
