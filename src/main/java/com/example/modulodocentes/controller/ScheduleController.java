package com.example.modulodocentes.controller;

// Versión: 1.0.5 - Implementación completa de CRUD para todas las entidades
// Última actualización: 19/06/2025 - Endpoints completos para horarios, asignaciones, asignaturas y aulas
// Patrones: Controller (parte del patrón MVC)
// Principios SOLID: Single Responsibility (solo maneja solicitudes HTTP), Open/Closed (extensible con nuevos endpoints)
// Antipatrones evitados: No se implementa lógica de negocio (evita Transaction Script)
import com.example.modulodocentes.model.AsignacionClase;
import com.example.modulodocentes.model.Asignatura;
import com.example.modulodocentes.model.Aula;
import com.example.modulodocentes.model.Horario;
import com.example.modulodocentes.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // --- Endpoints para Horarios ---

    // Obtener todos los horarios
    @GetMapping("/horarios")
    public ResponseEntity<List<Horario>> getHorarios() {
        List<Horario> horarios = scheduleService.getHorarios();
        return ResponseEntity.ok(horarios);
    }

    // Obtener un horario por ID
    @GetMapping("/horarios/{id}")
    public ResponseEntity<Horario> getHorarioById(@PathVariable String id) {
        Optional<Horario> horario = scheduleService.getHorarioRepository().findById(id);
        return horario.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo horario
    @PostMapping("/horarios")
    public ResponseEntity<Horario> createHorario(@RequestBody Horario horario) {
        Horario savedHorario = scheduleService.getHorarioRepository().save(horario);
        return ResponseEntity.status(201).body(savedHorario); // 201 Created
    }

    // Actualizar un horario
    @PutMapping("/horarios/{id}")
    public ResponseEntity<Horario> updateHorario(@PathVariable String id, @RequestBody Horario horario) {
        Optional<Horario> existingHorario = scheduleService.getHorarioRepository().findById(id);
        if (existingHorario.isPresent()) {
            horario.setId(id); // Asegura que el ID no cambie
            Horario updatedHorario = scheduleService.getHorarioRepository().save(horario);
            return ResponseEntity.ok(updatedHorario);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar un horario
    @DeleteMapping("/horarios/{id}")
    public ResponseEntity<Void> deleteHorario(@PathVariable String id) {
        if (scheduleService.getHorarioRepository().existsById(id)) {
            scheduleService.getHorarioRepository().deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build();
    }

    // --- Endpoints para Asignaciones ---

    // Obtener todas las asignaciones
    @GetMapping("/asignaciones")
    public ResponseEntity<List<AsignacionClase>> getAsignaciones() {
        List<AsignacionClase> asignaciones = scheduleService.getAsignaciones();
        return ResponseEntity.ok(asignaciones);
    }

    // Obtener una asignación por ID
    @GetMapping("/asignaciones/{id}")
    public ResponseEntity<AsignacionClase> getAsignacionById(@PathVariable String id) {
        Optional<AsignacionClase> asignacion = scheduleService.getAsignacionClaseRepository().findById(id);
        return asignacion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva asignación
    @PostMapping("/asignaciones")
    public ResponseEntity<AsignacionClase> createAsignacion(@RequestBody AsignacionClase asignacion) {
        AsignacionClase savedAsignacion = scheduleService.getAsignacionClaseRepository().save(asignacion);
        return ResponseEntity.status(201).body(savedAsignacion); // 201 Created
    }

    // Actualizar una asignación
    @PutMapping("/asignaciones/{id}")
    public ResponseEntity<AsignacionClase> updateAsignacion(@PathVariable String id, @RequestBody AsignacionClase asignacion) {
        Optional<AsignacionClase> existingAsignacion = scheduleService.getAsignacionClaseRepository().findById(id);
        if (existingAsignacion.isPresent()) {
            asignacion.setId(id); // Asegura que el ID no cambie
            AsignacionClase updatedAsignacion = scheduleService.getAsignacionClaseRepository().save(asignacion);
            return ResponseEntity.ok(updatedAsignacion);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar una asignación
    @DeleteMapping("/asignaciones/{id}")
    public ResponseEntity<Void> deleteAsignacion(@PathVariable String id) {
        if (scheduleService.getAsignacionClaseRepository().existsById(id)) {
            scheduleService.getAsignacionClaseRepository().deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build();
    }

    // --- Endpoints para Asignaturas ---

    // Obtener todas las asignaturas
    @GetMapping("/asignaturas")
    public ResponseEntity<List<Asignatura>> getAsignaturas() {
        List<Asignatura> asignaturas = scheduleService.getAsignaturaRepository().findAll();
        return ResponseEntity.ok(asignaturas);
    }

    // Obtener una asignatura por ID
    @GetMapping("/asignaturas/{id}")
    public ResponseEntity<Asignatura> getAsignaturaById(@PathVariable String id) {
        Optional<Asignatura> asignatura = scheduleService.getAsignaturaRepository().findById(id);
        return asignatura.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva asignatura
    @PostMapping("/asignaturas")
    public ResponseEntity<Asignatura> createAsignatura(@RequestBody Asignatura asignatura) {
        Asignatura savedAsignatura = scheduleService.getAsignaturaRepository().save(asignatura);
        return ResponseEntity.status(201).body(savedAsignatura); // 201 Created
    }

    // Actualizar una asignatura
    @PutMapping("/asignaturas/{id}")
    public ResponseEntity<Asignatura> updateAsignatura(@PathVariable String id, @RequestBody Asignatura asignatura) {
        Optional<Asignatura> existingAsignatura = scheduleService.getAsignaturaRepository().findById(id);
        if (existingAsignatura.isPresent()) {
            asignatura.setId(id); // Asegura que el ID no cambie
            Asignatura updatedAsignatura = scheduleService.getAsignaturaRepository().save(asignatura);
            return ResponseEntity.ok(updatedAsignatura);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar una asignatura
    @DeleteMapping("/asignaturas/{id}")
    public ResponseEntity<Void> deleteAsignatura(@PathVariable String id) {
        if (scheduleService.getAsignaturaRepository().existsById(id)) {
            scheduleService.getAsignaturaRepository().deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build();
    }

    // --- Endpoints para Aulas ---

    // Obtener todas las aulas
    @GetMapping("/aulas")
    public ResponseEntity<List<Aula>> getAulas() {
        List<Aula> aulas = scheduleService.getAulaRepository().findAll();
        return ResponseEntity.ok(aulas);
    }

    // Obtener una aula por ID
    @GetMapping("/aulas/{id}")
    public ResponseEntity<Aula> getAulaById(@PathVariable String id) {
        Optional<Aula> aula = scheduleService.getAulaRepository().findById(id);
        return aula.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva aula
    @PostMapping("/aulas")
    public ResponseEntity<Aula> createAula(@RequestBody Aula aula) {
        Aula savedAula = scheduleService.getAulaRepository().save(aula);
        return ResponseEntity.status(201).body(savedAula); // 201 Created
    }

    // Actualizar una aula
    @PutMapping("/aulas/{id}")
    public ResponseEntity<Aula> updateAula(@PathVariable String id, @RequestBody Aula aula) {
        Optional<Aula> existingAula = scheduleService.getAulaRepository().findById(id);
        if (existingAula.isPresent()) {
            aula.setId(id); // Asegura que el ID no cambie
            Aula updatedAula = scheduleService.getAulaRepository().save(aula);
            return ResponseEntity.ok(updatedAula);
        }
        return ResponseEntity.notFound().build();
    }

    // Eliminar una aula
    @DeleteMapping("/aulas/{id}")
    public ResponseEntity<Void> deleteAula(@PathVariable String id) {
        if (scheduleService.getAulaRepository().existsById(id)) {
            scheduleService.getAulaRepository().deleteById(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.notFound().build();
    }
}