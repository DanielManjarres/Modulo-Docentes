package com.example.modulodocentes.service;

// Versión: 1.0.1 - Pruebas unitarias ajustadas para estructura de paquete
// Última actualización: 17/06/2025 - Corrección de paquete y método findByIdentificacion
// Patrones: Unit Testing (verifica comportamiento del servicio)
// Principios SOLID: Single Responsibility (cada prueba verifica un caso específico), Dependency Inversion (usa mocks)
// Antipatrones evitados: No se prueba la implementación de repositorio directamente (evita Integration Test excesivo)
import com.example.modulodocentes.model.Docente;
import com.example.modulodocentes.repository.DocenteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DocenteServiceTest {

    @Mock
    private DocenteRepository docenteRepository;

    @InjectMocks
    private DocenteService docenteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDocente_Success() {
        // Arrange
        Docente docente = Docente.builder()
                .identificacion("12345")
                .correo("docente@example.com")
                .estado("activo")
                .nombre("Juan")
                .apellido("Perez")
                .telefono("123456789")
                .especialidad("Matemáticas")
                .build();
        when(docenteRepository.findByIdentificacion("12345")).thenReturn(Optional.empty());
        when(docenteRepository.save(docente)).thenReturn(docente);

        // Act
        Docente result = docenteService.createDocente(docente);

        // Assert
        assertNotNull(result);
        assertEquals("12345", result.getIdentificacion());
        verify(docenteRepository, times(1)).save(docente);
    }

    @Test
    void createDocente_DuplicateIdentification_ThrowsException() {
        // Arrange
        Docente docente = Docente.builder()
                .identificacion("12345")
                .correo("docente@example.com")
                .build();
        when(docenteRepository.findByIdentificacion("12345")).thenReturn(Optional.of(docente));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> docenteService.createDocente(docente));
        verify(docenteRepository, never()).save(any());
    }

    @Test
    void getDocenteById_Success() {
        // Arrange
        String id = "682ff75fd34365779ed60aad";
        Docente docente = Docente.builder().id(id).build();
        when(docenteRepository.findById(id)).thenReturn(Optional.of(docente));

        // Act
        Optional<Docente> result = docenteService.getDocenteById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void updateDocente_Success() {
        // Arrange
        String id = "682ff75fd34365779ed60aad";
        Docente existingDocente = Docente.builder().id(id).nombre("Juan").build();
        Docente updatedDetails = Docente.builder().nombre("Pedro").build();
        when(docenteRepository.findById(id)).thenReturn(Optional.of(existingDocente));
        when(docenteRepository.save(existingDocente)).thenReturn(existingDocente);

        // Act
        Docente result = docenteService.updateDocente(id, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals("Pedro", result.getNombre());
        verify(docenteRepository, times(1)).save(existingDocente);
    }

    @Test
    void deleteDocente_Success() {
        // Arrange
        String id = "682ff75fd34365779ed60aad";
        when(docenteRepository.existsById(id)).thenReturn(true);

        // Act
        docenteService.deleteDocente(id);

        // Assert
        verify(docenteRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteDocente_NotFound_ThrowsException() {
        // Arrange
        String id = "nonexistent";
        when(docenteRepository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> docenteService.deleteDocente(id));
        verify(docenteRepository, never()).deleteById(any());
    }
}