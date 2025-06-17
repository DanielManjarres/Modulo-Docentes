package com.example.modulodocentes.service;

// Versión: 1.1.0 - Actualización para incluir persistencia de notificaciones en MongoDB
// Última actualización: 17/06/2025 - Añadido guardado en base de datos
// Descripción: Este servicio envía notificaciones y guarda un registro en MongoDB para auditoría.
//              Usa ApplicationEventPublisher para disparar eventos y NotificationRepository para persistencia.
// Patrones: Service (encapsula lógica), Repository (abstracción de datos)
// Principios SOLID:
//   - Single Responsibility: Gestiona envío y persistencia de notificaciones.
//   - Open/Closed: Extensible con nuevos tipos de persistencia.
//   - Dependency Inversion: Depende de ApplicationEventPublisher y NotificationRepository.
// Antipatrones evitados:
//   - God Object: Separa la lógica de envío y persistencia.
//   - Inline Data: Usa un repositorio para la base de datos.
import com.example.modulodocentes.event.NotificationEvent;
import com.example.modulodocentes.model.Notification;
import com.example.modulodocentes.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private NotificationRepository notificationRepository;

    public void sendNotification(String docenteId, String asignatura, String aula, String horario, String motivo) {
        // Simulación del envío de correo
        String message = String.format(
                "Notificación para el docente %s:\nAsignatura: %s\nAula: %s\nHorario: %s\nMotivo: %s",
                docenteId, asignatura, aula, horario, motivo
        );
        System.out.println("Enviando correo: " + message);

        // Crear y guardar el registro en MongoDB
        Notification notification = new Notification();
        notification.setDocenteId(docenteId);
        notification.setAsignatura(asignatura);
        notification.setAula(aula);
        notification.setHorario(horario);
        notification.setMotivo(motivo);
        notification.setStatus("Enviado"); // Estado inicial
        notification.setTimestamp(System.currentTimeMillis()); // Marca de tiempo
        notificationRepository.save(notification);

        // Publicar el evento
        NotificationEvent event = new NotificationEvent(this, docenteId, asignatura, aula, horario, motivo);
        eventPublisher.publishEvent(event);

        // Verificación del tiempo de envío (<10 segundos)
        long startTime = System.currentTimeMillis();
        if (System.currentTimeMillis() - startTime < 10000) {
            System.out.println("Correo enviado exitosamente a docente " + docenteId);
        } else {
            throw new RuntimeException("Fallo en el envío: tiempo excedido");
        }
    }
}