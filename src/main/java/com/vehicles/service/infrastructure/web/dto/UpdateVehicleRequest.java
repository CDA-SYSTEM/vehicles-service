package com.vehicles.service.infrastructure.web.dto;

import com.vehicles.service.domain.model.command.UpdateVehicleCommand;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateVehicleRequest(
        @NotBlank(message = "El id de cliente es obligatorio")
        String clienteId,
        @NotNull(message = "La marca es obligatoria")
        Long marcaId,
        @NotNull(message = "La clase es obligatoria")
        Long claseId,
        @NotNull(message = "La línea es obligatoria")
        Long lineaId,
        @NotNull(message = "El color es obligatorio")
        Long colorId,
        @NotNull(message = "El tipo de vehículo es obligatorio")
        Long tipoVehiculoId,
        @NotNull(message = "El tipo de combustible es obligatorio")
        Long tipoCombustibleId,
        @NotNull(message = "El tipo de servicio es obligatorio")
        Long tipoServicioId,
        @NotBlank(message = "El modelo es obligatorio")
        String modelo,
        @NotBlank(message = "La placa es obligatoria")
        String placa,
        String certificadoNo
) {
    public UpdateVehicleCommand toCommand() {
        return new UpdateVehicleCommand(
                clienteId,
                marcaId,
                claseId,
                lineaId,
                colorId,
                tipoVehiculoId,
                tipoCombustibleId,
                tipoServicioId,
                modelo,
                placa,
                certificadoNo
        );
    }
}
