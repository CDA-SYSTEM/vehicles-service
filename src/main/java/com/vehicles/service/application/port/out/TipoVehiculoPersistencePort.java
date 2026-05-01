package com.vehicles.service.application.port.out;

import com.vehicles.service.domain.model.TipoVehiculo;

import java.util.List;
import java.util.Optional;

public interface TipoVehiculoPersistencePort {

    TipoVehiculo save(TipoVehiculo tipoVehiculo);

    Optional<TipoVehiculo> findById(Long id);

    List<TipoVehiculo> findAll();

    void deleteById(Long id);
}