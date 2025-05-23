package com.example.modulodocentes.service;

import com.example.modulodocentes.model.Docente;
import com.example.modulodocentes.repository.DocenteRepository;
import com.example.modulodocentes.strategy.ActiveDocenteValidation;
import com.example.modulodocentes.strategy.InactiveDocenteValidation;
import com.example.modulodocentes.strategy.ValidationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    private final Map<String, ValidationStrategy<Docente>> validationStrategies;

    public DocenteService() {
        validationStrategies = new HashMap<>();
        validationStrategies.put("activo", new ActiveDocenteValidation());
        validationStrategies.put("inactivo", new InactiveDocenteValidation());
    }

    public Docente createDocente(Docente docente) {
        ValidationStrategy<Docente> strategy = validationStrategies.get(docente.getEstado());
        if (strategy != null) {
            strategy.validate(docente);
        }
        return docenteRepository.save(docente);
    }

    public List<Docente> getAllDocentes() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> getDocenteById(String id) {
        return docenteRepository.findById(id);
    }

    public Docente updateDocente(String id, Docente docenteDetails) {
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        ValidationStrategy<Docente> strategy = validationStrategies.get(docenteDetails.getEstado());
        if (strategy != null) {
            strategy.validate(docenteDetails);
        }
        docente.setIdentificacion(docenteDetails.getIdentificacion());
        docente.setCorreo(docenteDetails.getCorreo());
        docente.setEstado(docenteDetails.getEstado());
        docente.setNombre(docenteDetails.getNombre());
        docente.setApellido(docenteDetails.getApellido());
        docente.setTelefono(docenteDetails.getTelefono());
        docente.setEspecialidad(docenteDetails.getEspecialidad());
        return docenteRepository.save(docente);
    }

    public void deleteDocente(String id) {
        docenteRepository.deleteById(id);
    }
}