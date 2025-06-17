package com.example.modulodocentes.strategy;

// Versión: 1.1.4 - Añadida normalización de tipo_aula y manejo de mayúsculas
// Última actualización: 17/06/2025 - Mejora en validación de tipo_aula
// Patrones: Strategy (implementa la validación de preferencias)
// Principios SOLID: Single Responsibility (solo valida), Liskov Substitution (cumple con ValidationStrategy)
// Antipatrones evitados: No se mezcla lógica de negocio (evita God Object)
import com.example.modulodocentes.model.Preferencia;
import com.example.modulodocentes.repository.DocenteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PreferenciaValidation implements ValidationStrategy<Preferencia> {

    private static final Logger logger = LoggerFactory.getLogger(PreferenciaValidation.class);

    @Autowired
    private DocenteRepository docenteRepository;

    @Override
    public void validate(Preferencia preferencia) {
        logger.info("Validating preferencia with docenteId: {}", preferencia.getDocenteId());
        if (docenteRepository == null) {
            logger.error("docenteRepository is null - Autowired failed");
            throw new IllegalStateException("DocenteRepository no está inicializado");
        }
        if (preferencia.getDocenteId() == null) {
            logger.error("docenteId is null");
            throw new IllegalArgumentException("El docenteId no puede ser nulo");
        }
        boolean exists = docenteRepository.existsById(preferencia.getDocenteId());
        logger.info("Docente with ID {} exists: {}", preferencia.getDocenteId(), exists);
        if (!exists) {
            logger.error("Docente with ID {} does not exist", preferencia.getDocenteId());
            throw new IllegalArgumentException("El docenteId no es válido o no existe");
        }
        if (preferencia.getSede_preferida() == null || preferencia.getSede_preferida().trim().isEmpty()) {
            throw new IllegalArgumentException("La sede preferida no puede estar vacía");
        }
        if (preferencia.getTipo_aula() == null || !preferencia.getTipo_aula().toLowerCase().matches("teórica|laboratorio")) {
            throw new IllegalArgumentException("El tipo de aula debe ser 'teórica' o 'laboratorio'");
        }
        if (preferencia.getJornada_preferida() == null || preferencia.getJornada_preferida().isEmpty()) {
            throw new IllegalArgumentException("La jornada preferida no puede estar vacía");
        }
    }
}