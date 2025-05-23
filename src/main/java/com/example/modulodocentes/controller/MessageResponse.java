package com.example.modulodocentes.controller;

public class MessageResponse {
    private String message;
    private Object data;

    public MessageResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}