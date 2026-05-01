package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.TipoServicio;

public record TipoServicioResponse(
        Long id,
        String nombre
) {
    public static TipoServicioResponse from(TipoServicio tipoServicio) {
        return new TipoServicioResponse(tipoServicio.getId(), tipoServicio.getNombre());
    }
}