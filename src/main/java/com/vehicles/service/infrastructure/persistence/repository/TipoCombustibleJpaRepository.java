package com.vehicles.service.infrastructure.persistence.repository;

import com.vehicles.service.infrastructure.persistence.entity.TipoCombustibleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoCombustibleJpaRepository extends JpaRepository<TipoCombustibleEntity, Long> {
}
