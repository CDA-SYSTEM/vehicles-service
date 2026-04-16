package com.vehicles.service.infrastructure.persistence.repository;

import com.vehicles.service.infrastructure.persistence.entity.TipoServicioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoServicioJpaRepository extends JpaRepository<TipoServicioEntity, Long> {
}
