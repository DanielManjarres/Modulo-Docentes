package com.example.modulodocentes.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "preferencias")
public class Preferencia {
    @Id
    private String id;

    @Indexed
    private String docenteId;

    private String sede_preferida;

    private String tipo_aula;
    private String jornada_preferida;
}