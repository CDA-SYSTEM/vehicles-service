package com.vehicles.service.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "marca")
public class MarcaEntity extends ReferenceDataEntity {

    public MarcaEntity() {
        super();
    }

    public MarcaEntity(Long id, String nombre, String descripcion) {
        super(id, nombre, descripcion);
    }
}
