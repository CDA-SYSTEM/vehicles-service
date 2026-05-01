package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.TipoCombustible;

public record TipoCombustibleResponse(
        Long id,
        String nombre
) {
    public static TipoCombustibleResponse from(TipoCombustible tipoCombustible) {
        return new TipoCombustibleResponse(tipoCombustible.getId(), tipoCombustible.getNombre());
    }
}