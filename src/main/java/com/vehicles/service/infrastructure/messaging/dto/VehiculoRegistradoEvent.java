package com.vehicles.service.infrastructure.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public record VehiculoRegistradoEvent(
        @JsonProperty("cliente_id") String clienteId,
        @JsonProperty("placa") String placa,
        @JsonProperty("marca") String marca
) implements Serializable {
    static final long serialVersionUID = 1L;
}
