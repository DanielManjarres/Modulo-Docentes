package com.example.modulodocentes.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "docentes")
public class Docente {
    @Id
    private String id;

    @Indexed(unique = true)
    private String identificacion;

    @Indexed(unique = true)
    private String correo;

    private String estado;

    private String nombre;

    private String apellido;

    private String telefono;

    private String especialidad;
}