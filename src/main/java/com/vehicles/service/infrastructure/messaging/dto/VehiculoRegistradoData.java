package com.vehicles.service.infrastructure.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

public record VehiculoRegistradoData(
        @JsonProperty("placa") String placa,
        @JsonProperty("marca") String marca,
        @JsonProperty("modelo") Integer modelo,
        @JsonProperty("tipo") String tipo,
        @JsonProperty("propietarioId") String propietarioId
) implements Serializable {
    static final long serialVersionUID = 1L;
}
