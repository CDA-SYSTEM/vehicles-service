package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.TipoServicio;
import jakarta.validation.constraints.NotBlank;

public record TipoServicioRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre
) {
    public TipoServicio toDomain() {
        return new TipoServicio(null, nombre);
    }
}