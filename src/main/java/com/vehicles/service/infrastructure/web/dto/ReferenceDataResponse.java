package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.reference.ReferenceData;

public record ReferenceDataResponse(
        Long id,
        String nombre,
        String descripcion
) {
    public static ReferenceDataResponse from(ReferenceData data) {
        return new ReferenceDataResponse(data.id(), data.nombre(), data.descripcion());
    }
}
