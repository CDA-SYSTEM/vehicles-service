package com.vehicles.service.infrastructure.persistence.repository;

import com.vehicles.service.infrastructure.persistence.entity.LineaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineaJpaRepository extends JpaRepository<LineaEntity, Long> {
}
