package com.vehicles.service.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "color")
public class ColorEntity extends ReferenceDataEntity {

    public ColorEntity() {
        super();
    }

    public ColorEntity(Long id, String nombre, String descripcion) {
        super(id, nombre, descripcion);
    }
}
