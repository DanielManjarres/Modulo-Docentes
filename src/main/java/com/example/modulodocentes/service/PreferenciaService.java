package com.example.modulodocentes.service;

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

    public Preferencia createPreferencia(Preferencia preferencia) {
        // Verificar si ya existe una preferencia para este docente
        List<Preferencia> existingPreferencias = preferenciaRepository.findByDocenteId(preferencia.getDocenteId());
        if (!existingPreferencias.isEmpty()) {
            throw new IllegalArgumentException("Ya existe una preferencia para este docente. Use la funcionalidad de actualización para modificarla.");
        }

        validationStrategy.validate(preferencia);
        preferencia.setTipo_aula(preferencia.getTipo_aula().toLowerCase());
        return preferenciaRepository.save(preferencia);
    }

    public List<Preferencia> getAllPreferencias() {
        return preferenciaRepository.findAll();
    }

    public Optional<Preferencia> getPreferenciaById(String id) {
        return preferenciaRepository.findById(id);
    }

    public List<Preferencia> getPreferenciasByDocenteId(String docenteId) {
        return preferenciaRepository.findByDocenteId(docenteId);
    }

    public List<Preferencia> getPreferenciasBySede(String sede) {
        return preferenciaRepository.findBySede_preferida(sede);
    }

    public Preferencia updatePreferencia(String id, Preferencia preferenciaDetails) {
        Preferencia preferencia = preferenciaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Preferencia no encontrada"));
        validationStrategy.validate(preferenciaDetails);
        preferencia.setDocenteId(preferenciaDetails.getDocenteId());
        preferencia.setSede_preferida(preferenciaDetails.getSede_preferida());
        preferencia.setTipo_aula(preferenciaDetails.getTipo_aula().toLowerCase());
        preferencia.setJornada_preferida(preferenciaDetails.getJornada_preferida());
        return preferenciaRepository.save(preferencia);
    }

    public void deletePreferencia(String id) {
        if (!preferenciaRepository.existsById(id)) {
            throw new RuntimeException("Preferencia no encontrada");
        }
        preferenciaRepository.deleteById(id);
    }
}