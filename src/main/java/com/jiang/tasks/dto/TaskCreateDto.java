package com.jiang.tasks.dto;

import com.jiang.tasks.Constants;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TaskCreateDto {
    private String title;
    private @DateTimeFormat(pattern = Constants.datePattern) Date dueDate;
}
