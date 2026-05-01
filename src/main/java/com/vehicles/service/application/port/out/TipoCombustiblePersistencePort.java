package com.vehicles.service.application.port.out;

import com.vehicles.service.domain.model.TipoCombustible;

import java.util.List;
import java.util.Optional;

public interface TipoCombustiblePersistencePort {

    TipoCombustible save(TipoCombustible tipoCombustible);

    Optional<TipoCombustible> findById(Long id);

    List<TipoCombustible> findAll();

    void deleteById(Long id);
}