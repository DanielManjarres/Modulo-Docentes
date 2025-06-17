package com.example.modulodocentes.model;

// Versión: 1.1.0 - Ajuste para restringir a una sola sede preferida
// Última actualización: 17/06/2025 - Cambio de sedes_preferidas a sede_preferida como String único
// Patrones: Modelo (parte del patrón MVC)
// Principios SOLID: Single Responsibility (solo define la estructura de datos)
// Antipatrones evitados: No se usa un modelo sobrecargado con lógica (evita God Object)
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "preferencias")
public class Preferencia {
    @Id
    private String id; // Identificador único generado por MongoDB

    @Indexed(unique = true) // Índice único para asegurar una preferencia por docente
    private String docenteId; // Referencia al docente, restringida a una por docente

    private String sede_preferida; // Campo único para la sede preferida (anteriormente List<String>)

    private String tipo_aula; // Debe ser 'teórica' o 'laboratorio' (normalizado a minúsculas)

    private String jornada_preferida; // Jornada preferida por el docente
}