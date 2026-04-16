package com.vehicles.service.application.port.out;

import com.vehicles.service.domain.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehiclePersistencePort {

    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findById(Long id);

    List<Vehicle> findAll();

    List<Vehicle> findByClienteId(String clienteId);

    void deleteById(Long id);
}
