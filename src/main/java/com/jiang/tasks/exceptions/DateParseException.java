package com.jiang.tasks.exceptions;

public class DateParseException extends RuntimeException {
    public DateParseException(String s) {
        super("Cannot parse the date " + s + ". Valid date format is DDMMYYYY.");
    }
}
