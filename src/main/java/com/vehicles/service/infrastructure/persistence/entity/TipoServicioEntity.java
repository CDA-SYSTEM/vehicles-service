package com.vehicles.service.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_servicio")
public class TipoServicioEntity extends ReferenceDataEntity {

    public TipoServicioEntity() {
        super();
    }

    public TipoServicioEntity(Long id, String nombre, String descripcion) {
        super(id, nombre, descripcion);
    }
}
