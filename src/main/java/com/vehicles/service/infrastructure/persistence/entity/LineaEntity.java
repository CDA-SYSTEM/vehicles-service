package com.vehicles.service.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "linea")
public class LineaEntity extends ReferenceDataEntity {

    public LineaEntity() {
        super();
    }

    public LineaEntity(Long id, String nombre) {
        super(id, nombre);
    }
}
