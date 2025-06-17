package com.example.modulodocentes.controller;

// Versión: 1.1.0 - Clase auxiliar para respuestas
// Última actualización: 17/06/2025 - Sin cambios
// Patrones: DTO (Data Transfer Object, implícito en respuestas)
// Principios SOLID: Single Responsibility (solo transporta datos)
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