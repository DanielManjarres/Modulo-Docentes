package com.example.modulodocentes.strategy;

// Versión: 1.1.0 - Interfaz para estrategia de validación
// Última actualización: 17/06/2025 - Sin cambios
// Patrones: Strategy (define el contrato para validaciones)
// Principios SOLID: Interface Segregation (método específico), Dependency Inversion (abstracción)
public interface ValidationStrategy<T> {
    void validate(T t);
}