package com.example.modulodocentes.repository;

// Versión: 1.0.1 - Repositorio con métodos personalizados
// Última actualización: 17/06/2025 - Añadido método findByIdentificacion
// Patrones: Repository (abstrae el acceso a datos)
// Principios SOLID: Single Responsibility (solo gestiona acceso a datos), Open/Closed (extensible con nuevos métodos)
// Antipatrones evitados: No se usa un acceso directo a la base de datos sin abstracción (evita God Object)
import com.example.modulodocentes.model.Docente;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface DocenteRepository extends MongoRepository<Docente, String> {
    // Busca un docente por su número de identificación
    Optional<Docente> findByIdentificacion(String identificacion);
}