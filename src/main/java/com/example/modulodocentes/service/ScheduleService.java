package com.example.modulodocentes.service;

// Versión: 1.0.9 - Añadido soporte para AulaRepository
// Última actualización: 19/06/2025 - Integración de aulas
// Patrones: Service (parte del patrón MVC), Strategy (potencial para validaciones futuras)
// Principios SOLID: Single Responsibility (solo maneja lógica de negocio), Open/Closed (extensible con nuevas funcionalidades)
// Antipatrones evitados: No se mezcla lógica de presentación (evita Smart UI), no se duplica código
import com.example.modulodocentes.model.AsignacionClase;
import com.example.modulodocentes.model.Horario;
import com.example.modulodocentes.repository.AsignacionClaseRepository;
import com.example.modulodocentes.repository.AsignaturaRepository;
import com.example.modulodocentes.repository.AulaRepository;
import com.example.modulodocentes.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {

    private final HorarioRepository horarioRepository;
    private final AsignacionClaseRepository asignacionClaseRepository;
    private final AsignaturaRepository asignaturaRepository;
    private final AulaRepository aulaRepository;

    @Autowired
    public ScheduleService(HorarioRepository horarioRepository, AsignacionClaseRepository asignacionClaseRepository,
                           AsignaturaRepository asignaturaRepository, AulaRepository aulaRepository) {
        this.horarioRepository = horarioRepository;
        this.asignacionClaseRepository = asignacionClaseRepository;
        this.asignaturaRepository = asignaturaRepository;
        this.aulaRepository = aulaRepository;
    }

    // Obtener horarios
    public List<Horario> getHorarios() {
        return horarioRepository.findAll();
    }

    // Obtener asignaciones
    public List<AsignacionClase> getAsignaciones() {
        return asignacionClaseRepository.findAll();
    }

    // Getters para los repositorios
    public HorarioRepository getHorarioRepository() {
        return horarioRepository;
    }

    public AsignacionClaseRepository getAsignacionClaseRepository() {
        return asignacionClaseRepository;
    }

    public AsignaturaRepository getAsignaturaRepository() {
        return asignaturaRepository;
    }

    public AulaRepository getAulaRepository() {
        return aulaRepository;
    }
}