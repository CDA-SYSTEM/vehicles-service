package com.vehicles.service.infrastructure.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

public record VehiculoRegistradoEvent(
        @JsonProperty("eventId") String eventId,
        @JsonProperty("timestamp") String timestamp,
        @JsonProperty("eventType") String eventType,
        @JsonProperty("data") VehiculoRegistradoData data
) implements Serializable {
    static final long serialVersionUID = 1L;

    public static VehiculoRegistradoEvent from(String placa, String marca, Integer modelo, String tipo, String propietarioId) {
        return new VehiculoRegistradoEvent(
                UUID.randomUUID().toString(),
                Instant.now().toString(),
                "VehiculoRegistrado",
                new VehiculoRegistradoData(placa, marca, modelo, tipo, propietarioId)
        );
    }
}
