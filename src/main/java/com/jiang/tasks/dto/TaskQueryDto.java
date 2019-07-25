package com.jiang.tasks.dto;

import com.jiang.tasks.Constants;
import com.jiang.tasks.exceptions.DateParseException;
import com.jiang.tasks.exceptions.StatusException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
public class TaskQueryDto {
    private String title;
    private Date from;
    private Date to;
    private Status status;

    public static TaskQueryDto convertToTaskQueryDto(Map<String, String> requestParams) {
        TaskQueryDto taskQueryDto = new TaskQueryDto();
        if (requestParams.get("title") != null) {
            taskQueryDto.setTitle(requestParams.get("title"));
        }
        if (requestParams.get("from") != null) {
            taskQueryDto.setFrom(Constants.convertStringToDate(requestParams.get("from")));
        }
        if (requestParams.get("to") != null) {
            taskQueryDto.setTo(Constants.convertStringToDate(requestParams.get("to")));
        }
        if (requestParams.get("status") != null) {
            try {
                taskQueryDto.setStatus(Status.valueOf(requestParams.get("status")));
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
                throw new StatusException(requestParams.get("status"));
            }
        }
        return taskQueryDto;
    }
}
