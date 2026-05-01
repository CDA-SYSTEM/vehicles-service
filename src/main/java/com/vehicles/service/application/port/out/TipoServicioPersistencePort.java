package com.vehicles.service.application.port.out;

import com.vehicles.service.domain.model.TipoServicio;

import java.util.List;
import java.util.Optional;

public interface TipoServicioPersistencePort {

    TipoServicio save(TipoServicio tipoServicio);

    Optional<TipoServicio> findById(Long id);

    List<TipoServicio> findAll();

    void deleteById(Long id);
}