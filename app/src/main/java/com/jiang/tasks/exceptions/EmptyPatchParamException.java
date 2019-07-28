package com.jiang.tasks.exceptions;

public class EmptyPatchParamException extends RuntimeException {
    public EmptyPatchParamException() {
        super("Patch parameters should not be empty");
    }
}
