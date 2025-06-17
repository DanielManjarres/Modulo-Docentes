package com.example.modulodocentes.event;

// Versión: 1.0.0 - Definición inicial del evento de notificación
// Última actualización: 17/06/2025 - Creación de la clase básica para encapsular datos de notificación
// Descripción: Esta clase representa un evento que se dispara cuando ocurre una notificación (asignación, cambio de aula o conflicto)
//              para un docente. Hereda de ApplicationEvent de Spring para integrarse con el sistema de eventos.
// Patrones: Event (componente clave del patrón Observer, permite que otros objetos reaccionen al evento)
// Principios SOLID:
//   - Single Responsibility: La clase se enfoca únicamente en definir la estructura del evento y sus datos.
//   - Dependency Inversion: Depende de la abstracción ApplicationEvent proporcionada por Spring, no de implementaciones concretas.
// Antipatrones evitados:
//   - God Object: No incluye lógica de negocio ni procesamiento, solo datos y acceso a ellos.
//   - Anemic Domain Model: Aunque es simple, cumple su propósito como contenedor de datos para el patrón Observer.
import org.springframework.context.ApplicationEvent;

public class NotificationEvent extends ApplicationEvent {
    // Atributos finales e inmutables para garantizar que el estado del evento no cambie después de su creación
    // Estos representan los datos clave de una notificación: identificador del docente, asignatura, aula, horario y motivo
    private final String docenteId;     // Identificador único del docente asociado al evento
    private final String asignatura;    // Nombre de la asignatura relacionada con la notificación
    private final String aula;          // Aula asignada o cambiada en la notificación
    private final String horario;       // Horario específico de la clase (ej. "Lunes 10:00")
    private final String motivo;        // Razón del evento (ej. "Cambio de aula", "Conflicto detectado")

    // Constructor que inicializa el evento con un origen (source) y los datos de la notificación
    // @param source: Objeto que dispara el evento (generalmente el NotificationService)
    // @param docenteId: Identificador del docente
    // @param asignatura: Nombre de la asignatura
    // @param aula: Aula involucrada
    // @param horario: Horario de la clase
    // @param motivo: Motivo del evento
    // Nota: Se usa super(source) para pasar el origen a la clase padre ApplicationEvent
    public NotificationEvent(Object source, String docenteId, String asignatura, String aula, String horario, String motivo) {
        super(source); // Llama al constructor de la clase padre para registrar el origen del evento
        this.docenteId = docenteId;    // Asigna el identificador del docente
        this.asignatura = asignatura;  // Asigna la asignatura
        this.aula = aula;             // Asigna el aula
        this.horario = horario;       // Asigna el horario
        this.motivo = motivo;         // Asigna el motivo
    }

    // Getter para obtener el identificador del docente
    // @return String: El docenteId asociado al evento
    // Nota: El uso de final asegura que el valor no se modifique después de la creación
    public String getDocenteId() {
        return docenteId;
    }

    // Getter para obtener la asignatura
    // @return String: El nombre de la asignatura
    public String getAsignatura() {
        return asignatura;
    }

    // Getter para obtener el aula
    // @return String: El aula asociada al evento
    public String getAula() {
        return aula;
    }

    // Getter para obtener el horario
    // @return String: El horario de la clase
    public String getHorario() {
        return horario;
    }

    // Getter para obtener el motivo
    // @return String: El motivo del evento
    public String getMotivo() {
        return motivo;
    }

    // Método heredado de ApplicationEvent para obtener el origen del evento
    // @return Object: El objeto que disparó el evento (proporcionado por Spring)
    // Nota: No se sobrescribe aquí, pero está disponible a través de super.getSource()
}