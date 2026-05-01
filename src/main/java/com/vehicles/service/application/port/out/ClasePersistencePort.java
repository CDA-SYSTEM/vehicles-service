package com.vehicles.service.application.port.out;

import com.vehicles.service.domain.model.Clase;

import java.util.List;
import java.util.Optional;

public interface ClasePersistencePort {

    Clase save(Clase clase);

    Optional<Clase> findById(Long id);

    List<Clase> findAll();

    void deleteById(Long id);
}