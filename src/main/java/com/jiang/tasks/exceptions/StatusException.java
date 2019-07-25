package com.jiang.tasks.exceptions;

public class StatusException extends RuntimeException {
    public StatusException(String s) {
        super(s + " is not a valid status");
    }
}