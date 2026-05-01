package com.vehicles.service.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_combustible")
public class TipoCombustibleEntity extends ReferenceDataEntity {

    public TipoCombustibleEntity() {
        super();
    }

    public TipoCombustibleEntity(Long id, String nombre) {
        super(id, nombre);
    }
}
