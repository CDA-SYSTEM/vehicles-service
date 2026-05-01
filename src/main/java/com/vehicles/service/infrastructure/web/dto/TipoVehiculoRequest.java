package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.TipoVehiculo;
import jakarta.validation.constraints.NotBlank;

public record TipoVehiculoRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre
) {
    public TipoVehiculo toDomain() {
        return new TipoVehiculo(null, nombre);
    }
}