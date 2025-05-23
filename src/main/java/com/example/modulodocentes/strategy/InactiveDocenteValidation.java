package com.example.modulodocentes.strategy;

import com.example.modulodocentes.model.Docente;

public class InactiveDocenteValidation implements ValidationStrategy<Docente> {
    @Override
    public void validate(Docente docente) {
        if (!"inactivo".equals(docente.getEstado())) {
            throw new IllegalArgumentException("El docente debe estar inactivo");
        }
    }
}