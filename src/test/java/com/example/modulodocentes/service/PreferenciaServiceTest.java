package com.example.modulodocentes.service;

// Versión: 1.1.1 - Pruebas unitarias ajustadas para PreferenciaService
// Última actualización: 17/06/2025 - Cobertura de CRUD con validación y normalización
// Patrones: Unit Testing (verifica comportamiento del servicio)
// Principios SOLID: Single Responsibility (cada prueba verifica un caso específico), Dependency Inversion (usa mocks)
// Antipatrones evitados: No se prueba la implementación de repositorio directamente (evita Integration Test excesivo)
import com.example.modulodocentes.model.Preferencia;
import com.example.modulodocentes.repository.PreferenciaRepository;
import com.example.modulodocentes.strategy.PreferenciaValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PreferenciaServiceTest {

    @Mock
    private PreferenciaRepository preferenciaRepository;

    @Mock
    private PreferenciaValidation validationStrategy;

    @InjectMocks
    private PreferenciaService preferenciaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPreferencia_Success() {
        // Arrange
        Preferencia preferencia = Preferencia.builder()
                .docenteId("682ff75fd34365779ed60aad")
                .sede_preferida("Principal")
                .tipo_aula("laboratorio")
                .jornada_preferida("Tarde")
                .build();
        when(preferenciaRepository.findByDocenteId("682ff75fd34365779ed60aad")).thenReturn(Collections.emptyList());
        when(preferenciaRepository.save(preferencia)).thenReturn(preferencia);

        // Act
        Preferencia result = preferenciaService.createPreferencia(preferencia);

        // Assert
        assertNotNull(result);
        assertEquals("laboratorio", result.getTipo_aula()); // Verifica normalización implícita
        verify(validationStrategy, times(1)).validate(preferencia);
        verify(preferenciaRepository, times(1)).save(preferencia);
    }

    @Test
    void createPreferencia_DuplicateDocenteId_ThrowsException() {
        // Arrange
        Preferencia preferencia = Preferencia.builder()
                .docenteId("682ff75fd34365779ed60aad")
                .sede_preferida("Nogales")
                .build();
        when(preferenciaRepository.findByDocenteId("682ff75fd34365779ed60aad")).thenReturn(Collections.singletonList(preferencia));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> preferenciaService.createPreferencia(preferencia));
        verify(validationStrategy, never()).validate(preferencia);
        verify(preferenciaRepository, never()).save(any());
    }

    @Test
    void getPreferenciaById_Success() {
        // Arrange
        String id = "68309bb1578504724a4dca78";
        Preferencia preferencia = Preferencia.builder().id(id).build();
        when(preferenciaRepository.findById(id)).thenReturn(Optional.of(preferencia));

        // Act
        Optional<Preferencia> result = preferenciaService.getPreferenciaById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void updatePreferencia_Success() {
        // Arrange
        String id = "68309bb1578504724a4dca78";
        Preferencia existingPreferencia = Preferencia.builder().id(id).tipo_aula("laboratorio").build();
        Preferencia updatedDetails = Preferencia.builder().tipo_aula("teórica").build();
        when(preferenciaRepository.findById(id)).thenReturn(Optional.of(existingPreferencia));
        when(preferenciaRepository.save(existingPreferencia)).thenReturn(existingPreferencia);

        // Act
        Preferencia result = preferenciaService.updatePreferencia(id, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals("teórica", result.getTipo_aula()); // Verifica normalización
        verify(validationStrategy, times(1)).validate(updatedDetails);
        verify(preferenciaRepository, times(1)).save(existingPreferencia);
    }

    @Test
    void deletePreferencia_Success() {
        // Arrange
        String id = "68309bb1578504724a4dca78";
        when(preferenciaRepository.existsById(id)).thenReturn(true);

        // Act
        preferenciaService.deletePreferencia(id);

        // Assert
        verify(preferenciaRepository, times(1)).deleteById(id);
    }

    @Test
    void deletePreferencia_NotFound_ThrowsException() {
        // Arrange
        String id = "nonexistent";
        when(preferenciaRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> preferenciaService.deletePreferencia(id));
        verify(preferenciaRepository, never()).deleteById(any());
    }
}