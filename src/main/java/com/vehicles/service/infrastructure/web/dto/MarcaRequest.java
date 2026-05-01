package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.Marca;
import jakarta.validation.constraints.NotBlank;

public record MarcaRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre
) {
    public Marca toDomain() {
        return new Marca(null, nombre);
    }
}