package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.Clase;

public record ClaseResponse(
        Long id,
        String nombre
) {
    public static ClaseResponse from(Clase clase) {
        return new ClaseResponse(clase.getId(), clase.getNombre());
    }
}