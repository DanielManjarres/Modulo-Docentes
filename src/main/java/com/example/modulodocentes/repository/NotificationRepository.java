package com.example.modulodocentes.repository;

// Versión: 1.2.0 - Añadido método countByStatus para estadísticas
// Última actualización: 17/06/2025 - Soporte para conteo por status
// Patrones: Repository (abstrae acceso a datos)
// Principios SOLID: Single Responsibility (solo gestiona datos)
// Antipatrones evitados: Inline Data (usa Spring Data para abstracción)
import com.example.modulodocentes.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByDocenteId(String docenteId);
    List<Notification> findByAsignatura(String asignatura);
    List<Notification> findByStatus(String status);
    List<Notification> findByDocenteIdAndStatus(String docenteId, String status);

    // Método para contar notificaciones por status
    long countByStatus(String status);
}