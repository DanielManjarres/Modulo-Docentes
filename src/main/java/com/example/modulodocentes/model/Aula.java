package com.example.modulodocentes.model;

// Versión: 1.0.0 - Modelo inicial para representar aulas con MongoDB
// Última actualización: 18/06/2025 - Añadida propiedad sede
// Patrones: Modelo (parte del patrón MVC)
// Principios SOLID: Single Responsibility (solo define la estructura de datos)
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "aulas")
public class Aula {
    @Id
    private String id; // ID generado por MongoDB
    private String nombre;
    private String tipo; // e.g., Teórica, Laboratorio
    private String detalles; // e.g., 40 puestos, TV
    private String sede; // Nueva propiedad añadida

    // Getters y Setters (proporcionados por @Data de Lombok)
}