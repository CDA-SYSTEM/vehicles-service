package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.TipoCombustible;
import jakarta.validation.constraints.NotBlank;

public record TipoCombustibleRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre
) {
    public TipoCombustible toDomain() {
        return new TipoCombustible(null, nombre);
    }
}