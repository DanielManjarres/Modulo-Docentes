package com.example.modulodocentes.model;

// Versión: 1.0.0 - Modelo inicial para representar docentes
// Última actualización: 17/06/2025 - Definición básica con campos requeridos
// Patrones: Modelo (parte del patrón MVC)
// Principios SOLID: Single Responsibility (solo define la estructura de datos)
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "docentes")
public class Docente {
    @Id
    private String id; // Identificador único generado por MongoDB

    private String identificacion; // Número de identificación único del docente
    private String correo; // Correo electrónico del docente
    private String estado; // Estado del docente (ej. "activo", "inactivo")
    private String nombre; // Nombre del docente
    private String apellido; // Apellido del docente
    private String telefono; // Teléfono de contacto
    private String especialidad; // Especialidad del docente
}