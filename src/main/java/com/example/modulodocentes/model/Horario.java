package com.example.modulodocentes.model;

// Versión: 1.0.1 - Añadidos campos para referencias manuales
// Última actualización: 19/06/2025 - Inclusión de asignaturaId, docenteId, aulaId
// Patrones: Modelo (parte del patrón MVC)
// Principios SOLID: Single Responsibility (solo define la estructura de datos)
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "horarios")
public class Horario {
    @Id
    private String id;
    private String dia;
    private String horaInicio;
    private String horaFin;
    private String asignaturaId; // Referencia manual a asignatura
    private String docenteId;    // Referencia manual a docente
    private String aulaId;       // Referencia manual a aula
    private String sede;

    // Getters y Setters (proporcionados por @Data de Lombok)
}