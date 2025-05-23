package com.example.modulodocentes.repository;

import com.example.modulodocentes.model.Docente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DocenteRepository extends MongoRepository<Docente, String> {
}