package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.Color;

public record ColorResponse(
        Long id,
        String nombre
) {
    public static ColorResponse from(Color color) {
        return new ColorResponse(color.getId(), color.getNombre());
    }
}