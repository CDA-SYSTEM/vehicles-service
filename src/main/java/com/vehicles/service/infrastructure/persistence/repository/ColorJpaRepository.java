package com.vehicles.service.infrastructure.persistence.repository;

import com.vehicles.service.infrastructure.persistence.entity.ColorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorJpaRepository extends JpaRepository<ColorEntity, Long> {
}
