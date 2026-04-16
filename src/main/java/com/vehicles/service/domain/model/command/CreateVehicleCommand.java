package com.vehicles.service.domain.model.command;

public record CreateVehicleCommand(
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
}
