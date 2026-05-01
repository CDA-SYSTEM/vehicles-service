package com.vehicles.service.application.port.out;

import com.vehicles.service.domain.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VehiclePersistencePort {

    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findById(Long id);

    Page<Vehicle> findAll(Pageable pageable);

    Page<Vehicle> findByClienteId(String clienteId, Pageable pageable);

    void deleteById(Long id);
}
