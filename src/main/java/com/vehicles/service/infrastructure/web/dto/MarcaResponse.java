package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.Marca;

public record MarcaResponse(
        Long id,
        String nombre
) {
    public static MarcaResponse from(Marca marca) {
        return new MarcaResponse(marca.getId(), marca.getNombre());
    }
}