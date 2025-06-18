package com.example.modulodocentes.service;

// Versión: 1.1.4 - Corrección de pruebas con configuración de tiempo por caso
// Última actualización: 17/06/2025 - Ajuste de spy por método para simular éxito y fallo
// Patrones: Unit Testing (valida el comportamiento del servicio)
// Principios SOLID: Single Responsibility (cada prueba verifica un caso específico)
// Antipatrones evitados: No se prueba integración real con base de datos
import com.example.modulodocentes.event.NotificationEvent;
import com.example.modulodocentes.model.Notification;
import com.example.modulodocentes.repository.NotificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Mock
    private NotificationRepository notificationRepository;

    @Spy
    @InjectMocks
    private NotificationService notificationService; // Usamos spy para la instancia real

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendNotification_Success() {
        String docenteId = "682ff75fd34365779ed60aad";
        String asignatura = "Matemáticas";
        String aula = "Aula 101";
        String horario = "Lunes 10:00";
        String motivo = "Cambio de aula";

        // Configurar spy para simular tiempo dentro de 10 segundos
        doReturn(5000L).when(notificationService).getCurrentTimeMillis();

        notificationService.sendNotification(docenteId, asignatura, aula, horario, motivo);

        verify(notificationRepository, times(1)).save(any(Notification.class));
        verify(eventPublisher, times(1)).publishEvent(any(NotificationEvent.class));
        verifyNoMoreInteractions(notificationRepository, eventPublisher);
    }

    @Test
    void sendNotification_TimeoutFailure() {
        String docenteId = "682ff75fd34365779ed60aad";
        String asignatura = "Matemáticas";
        String aula = "Aula 101";
        String horario = "Lunes 10:00";
        String motivo = "Cambio de aula";

        // Configurar spy para simular tiempo mayor a 10 segundos
        final long[] time = {10000L}; // Valor inicial
        doAnswer(invocation -> {
            long currentTime = time[0];
            time[0] += 11000L; // Incrementa el tiempo en 11 segundos para la segunda llamada
            return currentTime;
        }).when(notificationService).getCurrentTimeMillis();

        try {
            notificationService.sendNotification(docenteId, asignatura, aula, horario, motivo);
        } catch (RuntimeException e) {
            assertEquals("Fallo en el envío: tiempo excedido", e.getMessage());
            verify(notificationRepository, times(1)).save(any(Notification.class));
            verify(eventPublisher, times(1)).publishEvent(any(NotificationEvent.class));
            return;
        }
        fail("Se esperaba una excepción por tiempo excedido");
    }
}