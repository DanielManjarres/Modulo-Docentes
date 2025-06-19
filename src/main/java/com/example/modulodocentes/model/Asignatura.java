package com.example.modulodocentes.model;

// Versión: 1.0.0 - Modelo inicial para representar asignaturas con MongoDB
// Última actualización: 18/06/2025 - Definición básica
// Patrones: Modelo (parte del patrón MVC)
// Principios SOLID: Single Responsibility (solo define la estructura de datos)
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "asignaturas")
public class Asignatura {
    @Id
    private String id; // ID generado por MongoDB
    private String nombre;
    private String areaAcademica;

    // Getters y Setters (proporcionados por @Data de Lombok)
}