package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.response.VehicleResponse;

public record VehicleResponseDto(
        Long id,
        String clienteId,
        ReferenceDataResponse marca,
        ReferenceDataResponse clase,
        ReferenceDataResponse linea,
        ReferenceDataResponse color,
        ReferenceDataResponse tipoVehiculo,
        ReferenceDataResponse tipoCombustible,
        ReferenceDataResponse tipoServicio,
        String modelo,
        String placa,
        String certificadoNo
) {

    public static VehicleResponseDto from(VehicleResponse response) {
        return new VehicleResponseDto(
                response.id(),
                response.clienteId(),
                ReferenceDataResponse.from(response.marca()),
                ReferenceDataResponse.from(response.clase()),
                ReferenceDataResponse.from(response.linea()),
                ReferenceDataResponse.from(response.color()),
                ReferenceDataResponse.from(response.tipoVehiculo()),
                ReferenceDataResponse.from(response.tipoCombustible()),
                ReferenceDataResponse.from(response.tipoServicio()),
                response.modelo(),
                response.placa(),
                response.certificadoNo()
        );
    }
}
