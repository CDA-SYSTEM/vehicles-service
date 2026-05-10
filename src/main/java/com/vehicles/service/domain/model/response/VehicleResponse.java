package com.vehicles.service.domain.model.response;

import com.vehicles.service.domain.model.reference.ReferenceData;

public record VehicleResponse(
        Long id,
        String clienteId,
        ReferenceData marca,
        ReferenceData clase,
        ReferenceData linea,
        ReferenceData color,
        ReferenceData tipoVehiculo,
        ReferenceData tipoCombustible,
        ReferenceData tipoServicio,
        String modelo,
        String placa,
        String certificadoNo
) {
}
