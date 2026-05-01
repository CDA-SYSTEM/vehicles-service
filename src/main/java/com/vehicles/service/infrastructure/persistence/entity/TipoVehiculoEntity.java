package com.vehicles.service.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_vehiculo")
public class TipoVehiculoEntity extends ReferenceDataEntity {

    public TipoVehiculoEntity() {
        super();
    }

    public TipoVehiculoEntity(Long id, String nombre) {
        super(id, nombre);
    }
}
