package com.example.modulodocentes.controller;

// Versión: 1.0.0 - Definición inicial del controlador de notificaciones
// Última actualización: 17/06/2025 - Implementación del endpoint send con clase externa MessageResponse
// Descripción: Este controlador expone un endpoint REST para disparar notificaciones manualmente.
//              Maneja solicitudes HTTP y delega la lógica al NotificationService.
// Patrones: Controller (parte del patrón MVC)
// Principios SOLID:
//   - Single Responsibility: Solo gestiona solicitudes HTTP.
//   - Open/Closed: Permite añadir nuevos endpoints sin modificar el existente.
//   - Dependency Inversion: Depende de NotificationService como abstracción.
// Antipatrones evitados:
//   - Transaction Script: No incluye lógica de negocio, solo coordinación.
//   - Smart UI: Delega la lógica al servicio.
import com.example.modulodocentes.service.NotificationService;
import com.example.modulodocentes.controller.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

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
}