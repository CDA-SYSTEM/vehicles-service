package com.vehicles.service.infrastructure.messaging.dto;

import java.io.Serializable;

/**
 * DTO para la respuesta RPC de verificación de existencia de vehículo
 * vehicle-service responde con este objeto al form-service
 */
public record VehicleExistsResponse(
        boolean exists
) implements Serializable {
    static final long serialVersionUID = 1L;
}
