package com.example.modulodocentes.model;

// Versión: 1.0.2 - Ajuste del nombre de la colección para coincidir con MongoDB
// Última actualización: 19/06/2025 - Corrección de mapeo de colección
// Patrones: Modelo (parte del patrón MVC)
// Principios SOLID: Single Responsibility (solo define la estructura de datos)
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "asignacionesClase") // Ajuste aquí
public class AsignacionClase {
    @Id
    private String id;
    private String asignaturaId;
    private String docenteId;
    private String dia;
    private String hora;
    private String aulaId;
    private String detallesAula;
    private String areaAcademica;

    // Getters y Setters (proporcionados por @Data de Lombok)
}