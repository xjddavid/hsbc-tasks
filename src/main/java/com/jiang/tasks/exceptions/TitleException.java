package com.jiang.tasks.exceptions;

public class TitleException extends RuntimeException {
    public TitleException() {
        super("Title must be a valid string");
    }
}