package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.reference.ReferenceData;

public record ReferenceDataResponse(
        Long id,
        String nombre
) {
    public static ReferenceDataResponse from(ReferenceData data) {
        if (data == null) {
            return null;
        }
        return new ReferenceDataResponse(data.id(), data.nombre());
    }
}
