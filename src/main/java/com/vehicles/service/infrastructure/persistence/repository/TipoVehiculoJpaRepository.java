package com.vehicles.service.infrastructure.persistence.repository;

import com.vehicles.service.infrastructure.persistence.entity.TipoVehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoVehiculoJpaRepository extends JpaRepository<TipoVehiculoEntity, Long> {
}
