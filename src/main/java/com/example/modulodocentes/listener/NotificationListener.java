package com.example.modulodocentes.listener;

// Versión: 1.0.0 - Definición inicial del listener de notificaciones
// Última actualización: 17/06/2025 - Implementación del método onApplicationEvent
// Descripción: Este listener responde a eventos NotificationEvent, procesando notificaciones para la interfaz de usuario.
//              Simula la visualización de notificaciones en la interfaz.
// Patrones: Observer (escucha y reacciona a eventos publicados)
// Principios SOLID:
//   - Single Responsibility: Solo maneja la recepción y procesamiento de eventos.
//   - Open/Closed: Permite añadir nuevos manejadores sin modificar el código existente.
//   - Dependency Inversion: Depende de la interfaz ApplicationListener.
// Antipatrones evitados:
//   - Transaction Script: No mezcla lógica de negocio con la interfaz.
//   - Smart UI: Delega la lógica a eventos, no a la presentación directa.
import com.example.modulodocentes.event.NotificationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener implements ApplicationListener<NotificationEvent> {

    // Método que se ejecuta automáticamente cuando se recibe un evento NotificationEvent
    // @param event: El evento recibido del sistema de notificación
    // Descripción: Procesa el evento simulando una notificación en la interfaz de usuario.
    //              Imprime un mensaje como representación de la notificación visual.
    @Override
    public void onApplicationEvent(NotificationEvent event) {
        // Formatea un mensaje de notificación con los datos del evento
        String notification = String.format(
                "Notificación: Asignatura %s cambiada a aula %s en horario %s. Motivo: %s",
                event.getAsignatura(), event.getAula(), event.getHorario(), event.getMotivo()
        );
        System.out.println("Notificación en interfaz: " + notification); // Simulación de interfaz
    }
}