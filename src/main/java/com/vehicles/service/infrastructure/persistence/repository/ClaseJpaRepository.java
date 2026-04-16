package com.vehicles.service.infrastructure.persistence.repository;

import com.vehicles.service.infrastructure.persistence.entity.ClaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaseJpaRepository extends JpaRepository<ClaseEntity, Long> {
}
