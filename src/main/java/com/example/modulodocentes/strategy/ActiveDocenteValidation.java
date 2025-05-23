package com.example.modulodocentes.strategy;

import com.example.modulodocentes.model.Docente;

public class ActiveDocenteValidation implements ValidationStrategy<Docente> {
    @Override
    public void validate(Docente docente) {
        if (!"activo".equals(docente.getEstado())) {
            throw new IllegalArgumentException("El docente debe estar activo");
        }
        if (docente.getCorreo() == null || docente.getCorreo().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio para docentes activos");
        }
    }
}