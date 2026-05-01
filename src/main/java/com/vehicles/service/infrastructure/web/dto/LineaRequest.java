package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.Linea;
import jakarta.validation.constraints.NotBlank;

public record LineaRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre
) {
    public Linea toDomain() {
        return new Linea(null, nombre);
    }
}