package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.Clase;
import jakarta.validation.constraints.NotBlank;

public record ClaseRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre
) {
    public Clase toDomain() {
        return new Clase(null, nombre);
    }
}