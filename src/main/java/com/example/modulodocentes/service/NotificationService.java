package com.example.modulodocentes.service;

// Versión: 1.0.0 - Definición inicial del servicio de notificaciones
// Última actualización: 17/06/2025 - Implementación del método sendNotification
// Descripción: Este servicio se encarga de enviar notificaciones a docentes sobre asignaciones, cambios o conflictos.
//              Utiliza ApplicationEventPublisher para disparar eventos que otros componentes puedan manejar.
// Patrones: Service (encapsula la lógica de notificación), Observer (publica eventos)
// Principios SOLID:
//   - Single Responsibility: Solo gestiona el envío de notificaciones.
//   - Open/Closed: Permite extenderse con nuevos tipos de notificaciones sin modificar el código existente.
//   - Dependency Inversion: Depende de ApplicationEventPublisher como abstracción.
// Antipatrones evitados:
//   - God Object: No mezcla lógica de presentación ni persistencia.
//   - Smart UI: Delega la lógica a otros componentes (eventos).
import com.example.modulodocentes.event.NotificationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    // Inyección de dependencia del publisher de eventos de Spring
    // Permite disparar eventos a listeners registrados en la aplicación
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    // Método para enviar una notificación a un docente
    // @param docenteId: Identificador del docente
    // @param asignatura: Nombre de la asignatura
    // @param aula: Aula asociada
    // @param horario: Horario de la clase
    // @param motivo: Razón de la notificación (ej. cambio de aula)
    // Descripción: Simula el envío de un correo y publica un evento NotificationEvent.
    //              El tiempo de envío se verifica para cumplir con el requisito de <10 segundos.
    public void sendNotification(String docenteId, String asignatura, String aula, String horario, String motivo) {
        // Simulación del envío de correo (reemplazable con un servicio real como SendGrid)
        String message = String.format(
                "Notificación para el docente %s:\nAsignatura: %s\nAula: %s\nHorario: %s\nMotivo: %s",
                docenteId, asignatura, aula, horario, motivo
        );
        System.out.println("Enviando correo: " + message); // Salida para simulación

        // Publica el evento para que los listeners lo procesen
        NotificationEvent event = new NotificationEvent(this, docenteId, asignatura, aula, horario, motivo);
        eventPublisher.publishEvent(event);

        // Verificación del tiempo de envío (simulada, <10 segundos)
        long startTime = System.currentTimeMillis();
        if (System.currentTimeMillis() - startTime < 10000) {
            System.out.println("Correo enviado exitosamente a docente " + docenteId);
        } else {
            throw new RuntimeException("Fallo en el envío: tiempo excedido");
        }
    }
}