package com.vehicles.service.application.port.out;

import com.vehicles.service.domain.model.Marca;

import java.util.List;
import java.util.Optional;

public interface MarcaPersistencePort {

    Marca save(Marca marca);

    Optional<Marca> findById(Long id);

    List<Marca> findAll();

    void deleteById(Long id);
}