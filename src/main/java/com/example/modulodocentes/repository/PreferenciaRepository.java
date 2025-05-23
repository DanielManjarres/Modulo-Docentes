package com.example.modulodocentes.repository;

import com.example.modulodocentes.model.Preferencia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface PreferenciaRepository extends MongoRepository<Preferencia, String> {
    List<Preferencia> findByDocenteId(String docenteId);

    @Query("{ 'sede_preferida': ?0 }") //
    List<Preferencia> findBySede_preferida(String sede);
}