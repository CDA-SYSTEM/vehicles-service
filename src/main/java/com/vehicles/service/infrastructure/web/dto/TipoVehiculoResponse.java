package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.TipoVehiculo;

public record TipoVehiculoResponse(
        Long id,
        String nombre
) {
    public static TipoVehiculoResponse from(TipoVehiculo tipoVehiculo) {
        return new TipoVehiculoResponse(tipoVehiculo.getId(), tipoVehiculo.getNombre());
    }
}