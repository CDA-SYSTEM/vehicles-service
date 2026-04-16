package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.response.VehicleResponse;

public record VehicleResponseDto(
        Long id,
        String clienteId,
        Long marcaId,
        Long claseId,
        Long lineaId,
        Long colorId,
        Long tipoVehiculoId,
        Long tipoCombustibleId,
        Long tipoServicioId,
        String modelo,
        String placa,
        String certificadoNo
) {

    public static VehicleResponseDto from(VehicleResponse response) {
        return new VehicleResponseDto(
                response.id(),
                response.clienteId(),
                response.marcaId(),
                response.claseId(),
                response.lineaId(),
                response.colorId(),
                response.tipoVehiculoId(),
                response.tipoCombustibleId(),
                response.tipoServicioId(),
                response.modelo(),
                response.placa(),
                response.certificadoNo()
        );
    }
}
