package com.example.modulodocentes.repository;

// Versión: 1.1.1 - Añadido índice único en docenteId
// Última actualización: 17/06/2025 - Ajuste de método findBySede_preferida
// Patrones: Repository (abstrae el acceso a datos)
// Principios SOLID: Single Responsibility (solo gestiona acceso a datos), Open/Closed (extensible con nuevos métodos)
// Antipatrones evitados: No se accede directamente a la base de datos sin abstracción (evita God Object)
import com.example.modulodocentes.model.Preferencia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PreferenciaRepository extends MongoRepository<Preferencia, String> {
    // Busca todas las preferencias asociadas a un docenteId
    List<Preferencia> findByDocenteId(String docenteId);

    // Busca preferencias por una sede específica usando una consulta personalizada
    @Query("{ 'sede_preferida': ?0 }")
    List<Preferencia> findBySede_preferida(String sede);
}