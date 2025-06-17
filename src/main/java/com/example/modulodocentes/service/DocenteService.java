package com.example.modulodocentes.service;

// Versión: 1.0.2 - Implementación del CRUD con validaciones básicas
// Última actualización: 17/06/2025 - Añadida validación de duplicados por identificacion
// Patrones: Service (parte del patrón MVC), Strategy (potencial para validaciones futuras)
// Principios SOLID: Single Responsibility (solo maneja lógica de negocio), Open/Closed (extensible con nuevas validaciones)
// Antipatrones evitados: No se mezcla lógica de presentación (evita Smart UI), no se duplica código (evita Copy-Paste)
import com.example.modulodocentes.model.Docente;
import com.example.modulodocentes.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    // Crea un nuevo docente, verificando duplicados por identificacion
    public Docente createDocente(Docente docente) {
        if (docenteRepository.findByIdentificacion(docente.getIdentificacion()).isPresent()) {
            throw new IllegalStateException("Ya existe un docente con esta identificación.");
        }
        return docenteRepository.save(docente);
    }

    // Obtiene todos los docentes
    public List<Docente> getAllDocentes() {
        return docenteRepository.findAll();
    }

    // Obtiene un docente por su ID
    public Optional<Docente> getDocenteById(String id) {
        return docenteRepository.findById(id);
    }

    // Obtiene un docente por su identificacion
    public Optional<Docente> getDocenteByIdentificacion(String identificacion) {
        return docenteRepository.findByIdentificacion(identificacion);
    }

    // Actualiza un docente existente
    public Docente updateDocente(String id, Docente docenteDetails) {
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        docente.setCorreo(docenteDetails.getCorreo());
        docente.setEstado(docenteDetails.getEstado());
        docente.setNombre(docenteDetails.getNombre());
        docente.setApellido(docenteDetails.getApellido());
        docente.setTelefono(docenteDetails.getTelefono());
        docente.setEspecialidad(docenteDetails.getEspecialidad());
        return docenteRepository.save(docente);
    }

    // Elimina un docente por su ID
    public void deleteDocente(String id) {
        if (!docenteRepository.existsById(id)) {
            throw new RuntimeException("Docente no encontrado");
        }
        docenteRepository.deleteById(id);
    }
}