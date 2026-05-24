package com.vehicles.service.infrastructure.messaging.dto;

import java.io.Serializable;

public record VehicleCreatedEvent(
        String placa,
        String marca,
        String modelo,
        String tipo,
        String propietarioId
) implements Serializable {
    static final long serialVersionUID = 1L;
}
