package com.vehicles.service.infrastructure.messaging.dto;

import java.io.Serializable;

/**
 * DTO para la solicitud RPC de verificación de existencia de vehículo
 * form-service envía esta solicitud esperando una respuesta RPC
 */
public record VehicleExistsRequest(
        String id
) implements Serializable {
    static final long serialVersionUID = 1L;
}
