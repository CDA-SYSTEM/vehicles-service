package com.vehicles.service.infrastructure.persistence.repository;

import com.vehicles.service.infrastructure.persistence.entity.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;

import java.util.Optional;

public interface VehicleJpaRepository extends JpaRepository<VehicleEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"marca", "clase", "linea", "color", "tipoVehiculo", "tipoCombustible", "tipoServicio"})
    Optional<VehicleEntity> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"marca", "clase", "linea", "color", "tipoVehiculo", "tipoCombustible", "tipoServicio"})
    Page<VehicleEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"marca", "clase", "linea", "color", "tipoVehiculo", "tipoCombustible", "tipoServicio"})
    Page<VehicleEntity> findByClienteId(String clienteId, Pageable pageable);

    Optional<VehicleEntity> findByPlaca(String placa);
}
