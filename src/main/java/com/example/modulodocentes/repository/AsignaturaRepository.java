package com.example.modulodocentes.repository;

// Versión: 1.0.0 - Repositorio básico para Asignatura con MongoDB
// Última actualización: 18/06/2025 - Creación inicial
// Patrones: Repository (abstrae el acceso a datos)
// Principios SOLID: Single Responsibility (solo gestiona acceso a datos), Open/Closed (extensible con nuevos métodos)
// Antipatrones evitados: No se usa acceso directo a la base de datos sin abstracción
import com.example.modulodocentes.model.Asignatura;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AsignaturaRepository extends MongoRepository<Asignatura, String> {
}