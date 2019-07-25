package com.jiang.tasks.dto;

public enum Status {
    CREATED("CREATED"), DONE("DONE"), DELETED("DELETED");

    private String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}