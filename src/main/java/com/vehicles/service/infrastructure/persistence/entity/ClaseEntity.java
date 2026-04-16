package com.vehicles.service.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clase")
public class ClaseEntity extends ReferenceDataEntity {

    public ClaseEntity() {
        super();
    }

    public ClaseEntity(Long id, String nombre, String descripcion) {
        super(id, nombre, descripcion);
    }
}
