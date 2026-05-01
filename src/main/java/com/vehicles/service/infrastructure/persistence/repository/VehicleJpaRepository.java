package com.vehicles.service.infrastructure.persistence.repository;

import com.vehicles.service.infrastructure.persistence.entity.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleJpaRepository extends JpaRepository<VehicleEntity, Long> {

    Page<VehicleEntity> findByClienteId(String clienteId, Pageable pageable);

    Optional<VehicleEntity> findByPlaca(String placa);
}
