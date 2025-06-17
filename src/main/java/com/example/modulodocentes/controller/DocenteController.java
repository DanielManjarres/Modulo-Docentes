package com.example.modulodocentes.controller;

// Versión: 1.0.3 - Implementación de endpoints CRUD
// Última actualización: 17/06/2025 - Añadido manejo de excepciones específicas
// Patrones: Controller (parte del patrón MVC)
// Principios SOLID: Single Responsibility (solo maneja solicitudes HTTP), Open/Closed (extensible con nuevos endpoints)
// Antipatrones evitados: No se implementa lógica de negocio (evita Transaction Script)
import com.example.modulodocentes.model.Docente;
import com.example.modulodocentes.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/docentes")
public class DocenteController {

    @Autowired
    private DocenteService docenteService;

    // Crea un nuevo docente
    @PostMapping
    public ResponseEntity<?> createDocente(@RequestBody Docente docente) {
        try {
            Docente createdDocente = docenteService.createDocente(docente);
            return ResponseEntity.ok(createdDocente);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null));
        }
    }

    // Obtiene todos los docentes
    @GetMapping
    public List<Docente> getAllDocentes() {
        return docenteService.getAllDocentes();
    }

    // Obtiene un docente por ID
    @GetMapping("/{id}")
    public ResponseEntity<Docente> getDocenteById(@PathVariable String id) {
        return docenteService.getDocenteById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Obtiene un docente por identificacion
    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<Docente> getDocenteByIdentificacion(@PathVariable String identificacion) {
        return docenteService.getDocenteByIdentificacion(identificacion)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualiza un docente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocente(@PathVariable String id, @RequestBody Docente docenteDetails) {
        try {
            Docente updatedDocente = docenteService.updateDocente(id, docenteDetails);
            return ResponseEntity.ok(new MessageResponse("Docente actualizado correctamente", updatedDocente));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null));
        }
    }

    // Elimina un docente
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteDocente(@PathVariable String id) {
        try {
            docenteService.deleteDocente(id);
            return ResponseEntity.ok(new MessageResponse("Docente eliminado correctamente", null));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage(), null));
        }
    }
}