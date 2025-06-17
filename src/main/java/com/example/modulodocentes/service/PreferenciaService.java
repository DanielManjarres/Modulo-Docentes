package com.example.modulodocentes.service;

// Versión: 1.1.2 - Reforzada validación de una preferencia por docente
// Última actualización: 17/06/2025 - Normalización de tipo_aula y manejo de duplicados
// Patrones: Service (parte del patrón MVC), Strategy (usa ValidationStrategy)
// Principios SOLID: Single Responsibility (solo maneja lógica de negocio), Open/Closed (extensible con nuevas validaciones), Dependency Inversion (depende de abstracciones)
// Antipatrones evitados: No se mezcla lógica de presentación (evita Smart UI), no se duplica código (evita Copy-Paste)
import com.example.modulodocentes.model.Preferencia;
import com.example.modulodocentes.repository.PreferenciaRepository;
import com.example.modulodocentes.strategy.PreferenciaValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PreferenciaService {

    @Autowired
    private PreferenciaRepository preferenciaRepository;

    @Autowired
    private PreferenciaValidation validationStrategy;

    // Crea una nueva preferencia, verificando que no exista una para el docente
    public Preferencia createPreferencia(Preferencia preferencia) {
        // Validación para evitar duplicados (reforzada por índice único en docenteId)
        List<Preferencia> existingPreferencias = preferenciaRepository.findByDocenteId(preferencia.getDocenteId());
        if (!existingPreferencias.isEmpty()) {
            throw new IllegalStateException("Ya existe una preferencia para este docente. Use la actualización.");
        }
        validationStrategy.validate(preferencia);
        preferencia.setTipo_aula(preferencia.getTipo_aula().toLowerCase()); // Normalizar a minúsculas
        return preferenciaRepository.save(preferencia);
    }

    // Obtiene todas las preferencias
    public List<Preferencia> getAllPreferencias() {
        return preferenciaRepository.findAll();
    }

    // Obtiene una preferencia por su ID
    public Optional<Preferencia> getPreferenciaById(String id) {
        return preferenciaRepository.findById(id);
    }

    // Obtiene todas las preferencias de un docente
    public List<Preferencia> getPreferenciasByDocenteId(String docenteId) {
        return preferenciaRepository.findByDocenteId(docenteId);
    }

    // Obtiene preferencias por sede
    public List<Preferencia> getPreferenciasBySede(String sede) {
        return preferenciaRepository.findBySede_preferida(sede);
    }

    // Actualiza una preferencia existente
    public Preferencia updatePreferencia(String id, Preferencia preferenciaDetails) {
        Preferencia preferencia = preferenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Preferencia no encontrada"));
        validationStrategy.validate(preferenciaDetails);
        preferencia.setDocenteId(preferenciaDetails.getDocenteId());
        preferencia.setSede_preferida(preferenciaDetails.getSede_preferida());
        preferencia.setTipo_aula(preferenciaDetails.getTipo_aula().toLowerCase()); // Normalizar
        preferencia.setJornada_preferida(preferenciaDetails.getJornada_preferida());
        return preferenciaRepository.save(preferencia);
    }

    // Elimina una preferencia por su ID
    public void deletePreferencia(String id) {
        if (!preferenciaRepository.existsById(id)) {
            throw new RuntimeException("Preferencia no encontrada");
        }
        preferenciaRepository.deleteById(id);
    }
}