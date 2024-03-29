package com.jiang.tasks.dto;

import com.jiang.tasks.Utils;
import com.jiang.tasks.enums.Status;
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
            taskQueryDto.setFrom(Utils.convertStringToDate(requestParams.get("from")));
        }
        if (requestParams.get("to") != null) {
            taskQueryDto.setTo(Utils.convertStringToDate(requestParams.get("to")));
        }
        if (requestParams.get("status") != null) {
            if (!Status.contain(requestParams.get("status"))) {
                throw new StatusException(requestParams.get("status"));
            } else {
                taskQueryDto.setStatus(Status.valueOf(requestParams.get("status")));
            }
        }
        return taskQueryDto;
    }
}
