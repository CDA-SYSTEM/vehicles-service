package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.reference.ReferenceData;
import jakarta.validation.constraints.NotBlank;

public record ReferenceDataRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,
        String descripcion
) {
    public ReferenceData toDomain() {
        return new ReferenceData(null, nombre, descripcion);
    }
}
