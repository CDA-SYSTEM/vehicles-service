package com.vehicles.service.domain.model;

public record Vehicle(
        Long id,
        String clienteId,
        Long marcaId,
        String marcaNombre,
        Long claseId,
        String claseNombre,
        Long lineaId,
        String lineaNombre,
        Long colorId,
        String colorNombre,
        Long tipoVehiculoId,
        String tipoVehiculoNombre,
        Long tipoCombustibleId,
        String tipoCombustibleNombre,
        Long tipoServicioId,
        String tipoServicioNombre,
        String modelo,
        String placa,
        String certificadoNo
) {
}
