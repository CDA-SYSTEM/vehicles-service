package com.vehicles.service.application.port.out;

import com.vehicles.service.domain.model.Linea;

import java.util.List;
import java.util.Optional;

public interface LineaPersistencePort {

    Linea save(Linea linea);

    Optional<Linea> findById(Long id);

    List<Linea> findAll();

    void deleteById(Long id);
}