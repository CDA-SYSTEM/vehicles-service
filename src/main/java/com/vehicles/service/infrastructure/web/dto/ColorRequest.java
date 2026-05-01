package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.Color;
import jakarta.validation.constraints.NotBlank;

public record ColorRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre
) {
    public Color toDomain() {
        return new Color(null, nombre);
    }
}