package com.jiang.tasks.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseBody
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String taskNotFoundHandler(TaskNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String badRequestHandler(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        return "Not valid request";
    }

    @ResponseBody
    @ExceptionHandler(DateParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String wrongDateFormatHandler(DateParseException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(StatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String wrongStatusHandler(StatusException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(TitleException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String wrongTitleHandler(TitleException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(EmptyPatchParamException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String emptyPatchParamHandler(EmptyPatchParamException ex) {
        return ex.getMessage();
    }
}
