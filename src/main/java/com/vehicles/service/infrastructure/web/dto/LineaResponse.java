package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.Linea;

public record LineaResponse(
        Long id,
        String nombre
) {
    public static LineaResponse from(Linea linea) {
        return new LineaResponse(linea.getId(), linea.getNombre());
    }
}