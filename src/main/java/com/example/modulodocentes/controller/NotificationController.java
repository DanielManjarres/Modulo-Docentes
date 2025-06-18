package com.example.modulodocentes.controller;

// Versión: 1.3.0 - Implementación completa de todos los endpoints de notificaciones
// Última actualización: 17/06/2025 - Añadidos GET /{id}, PUT /{id}, DELETE /{id} y GET /statistics
// Descripción: Este controlador gestiona todos los endpoints REST para notificaciones, incluyendo envío, listado,
//              recuperación individual, actualización, eliminación y estadísticas.
// Patrones: Controller (parte del patrón MVC)
// Principios SOLID:
//   - Single Responsibility: Maneja solicitudes HTTP exclusivamente.
//   - Open/Closed: Permite añadir nuevos endpoints sin modificar los existentes.
//   - Dependency Inversion: Depende de NotificationService y NotificationRepository.
// Antipatrones evitados:
//   - Transaction Script: Delega lógica al servicio y repositorio.
//   - Smart UI: Separa la presentación de la lógica.
import com.example.modulodocentes.service.NotificationService;
import com.example.modulodocentes.model.Notification;
import com.example.modulodocentes.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private NotificationRepository notificationRepository;

    @PostMapping("/send")
    public ResponseEntity<?> sendNotification(
            @RequestParam String docenteId,
            @RequestParam String asignatura,
            @RequestParam String aula,
            @RequestParam String horario,
            @RequestParam String motivo) {
        try {
            notificationService.sendNotification(docenteId, asignatura, aula, horario, motivo);
            return ResponseEntity.ok(new MessageResponse("Notificación enviada exitosamente", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error al enviar notificación: " + e.getMessage(), null));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllNotifications(
            @RequestParam(required = false) String docenteId,
            @RequestParam(required = false) String asignatura,
            @RequestParam(required = false) String status) {
        try {
            List<Notification> notifications;
            if (docenteId != null && status != null) {
                notifications = notificationRepository.findByDocenteIdAndStatus(docenteId, status);
            } else if (docenteId != null) {
                notifications = notificationRepository.findByDocenteId(docenteId);
            } else if (asignatura != null) {
                notifications = notificationRepository.findByAsignatura(asignatura);
            } else if (status != null) {
                notifications = notificationRepository.findByStatus(status);
            } else {
                notifications = notificationRepository.findAll();
            }
            if (notifications.isEmpty()) {
                return ResponseEntity.ok(new MessageResponse("No hay notificaciones registradas", null));
            }
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error al recuperar notificaciones: " + e.getMessage(), null));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getNotificationById(@PathVariable String id) {
        try {
            return notificationRepository.findById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error al recuperar notificación: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateNotification(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        try {
            return notificationRepository.findById(id)
                    .map(notification -> {
                        updates.forEach((key, value) -> {
                            switch (key) {
                                case "status": notification.setStatus((String) value); break;
                                case "motivo": notification.setMotivo((String) value); break;
                                // Añadir más campos si es necesario
                            }
                        });
                        notificationRepository.save(notification);
                        return ResponseEntity.ok(new MessageResponse("Notificación actualizada", null));
                    })
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error al actualizar notificación: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable String id) {
        try {
            if (notificationRepository.existsById(id)) {
                notificationRepository.deleteById(id);
                return ResponseEntity.ok(new MessageResponse("Notificación eliminada", null));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error al eliminar notificación: " + e.getMessage(), null));
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getNotificationStatistics() {
        try {
            long total = notificationRepository.count();
            Map<String, Long> byStatus = new HashMap<>();
            for (String status : new String[]{"Enviado", "Fallido"}) {
                byStatus.put(status, notificationRepository.countByStatus(status));
            }
            Map<String, Object> stats = new HashMap<>();
            stats.put("total", total);
            stats.put("byStatus", byStatus);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error al calcular estadísticas: " + e.getMessage(), null));
        }
    }
}