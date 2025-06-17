package com.example.modulodocentes.model;

// Versión: 1.0.0 - Definición inicial del modelo de notificación
// Última actualización: 17/06/2025 - Creación del documento para MongoDB
// Descripción: Representa un registro de notificación en la base de datos.
// Patrones: Entity (mapeo a colección en MongoDB)
// Principios SOLID: Single Responsibility (solo define la estructura del documento)
// Antipatrones evitados: Anemic Domain Model (incluye atributos básicos)
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "notifications")
public class Notification {
    @Id
    private String id;
    private String docenteId;
    private String asignatura;
    private String aula;
    private String horario;
    private String motivo;
    private String status; // Ej. "Enviado", "Fallido"
    private Long timestamp; // Marca de tiempo en milisegundos
}