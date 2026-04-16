package com.vehicles.service.infrastructure.persistence.repository;

import com.vehicles.service.infrastructure.persistence.entity.MarcaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaJpaRepository extends JpaRepository<MarcaEntity, Long> {
}
