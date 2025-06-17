package com.example.modulodocentes.controller;

// Versión: 1.1.3 - Mejora en manejo de excepciones y endpoints
// Última actualización: 17/06/2025 - Añadido manejo específico de IllegalStateException
// Patrones: Controller (parte del patrón MVC)
// Principios SOLID: Single Responsibility (solo maneja solicitudes HTTP), Open/Closed (extensible con nuevos endpoints)
// Antipatrones evitados: No se implementa lógica de negocio (evita Transaction Script)
import com.example.modulodocentes.model.Preferencia;
import com.example.modulodocentes.service.PreferenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preferencias")
public class PreferenciaController {

    @Autowired
    private PreferenciaService preferenciaService;

    // Crea una nueva preferencia
    @PostMapping
    public ResponseEntity<?> createPreferencia(@RequestBody Preferencia preferencia) {
        try {
            Preferencia createdPreferencia = preferenciaService.createPreferencia(preferencia);
            return ResponseEntity.ok(createdPreferencia);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null));
        }
    }

    // Obtiene todas las preferencias
    @GetMapping
    public List<Preferencia> getAllPreferencias() {
        return preferenciaService.getAllPreferencias();
    }

    // Obtiene una preferencia por ID
    @GetMapping("/{id}")
    public ResponseEntity<Preferencia> getPreferenciaById(@PathVariable String id) {
        return preferenciaService.getPreferenciaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtiene preferencias por docenteId
    @GetMapping("/docente/{docenteId}")
    public ResponseEntity<List<Preferencia>> getPreferenciasByDocenteId(@PathVariable String docenteId) {
        List<Preferencia> preferencias = preferenciaService.getPreferenciasByDocenteId(docenteId);
        return ResponseEntity.ok(preferencias);
    }

    // Obtiene preferencias por sede
    @GetMapping("/sede/{sede}")
    public ResponseEntity<List<Preferencia>> getPreferenciasBySede(@PathVariable String sede) {
        List<Preferencia> preferencias = preferenciaService.getPreferenciasBySede(sede);
        return ResponseEntity.ok(preferencias);
    }

    // Actualiza una preferencia
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePreferencia(@PathVariable String id, @RequestBody Preferencia preferenciaDetails) {
        try {
            Preferencia updatedPreferencia = preferenciaService.updatePreferencia(id, preferenciaDetails);
            return ResponseEntity.ok(new MessageResponse("Preferencia actualizada correctamente", updatedPreferencia));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null));
        }
    }

    // Elimina una preferencia
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePreferencia(@PathVariable String id) {
        try {
            preferenciaService.deletePreferencia(id);
            return ResponseEntity.ok(new MessageResponse("Preferencia eliminada correctamente", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null));
        }
    }
}