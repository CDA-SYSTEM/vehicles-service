package com.vehicles.service.domain.model.reference;

import java.util.Arrays;
import java.util.Locale;

public enum ReferenceType {
    MARCA("marcas"),
    CLASE("clases"),
    LINEA("lineas"),
    COLOR("colores"),
    TIPO_VEHICULO("tipos-vehiculo"),
    TIPO_COMBUSTIBLE("tipos-combustible"),
    TIPO_SERVICIO("tipos-servicio");

    private final String path;

    ReferenceType(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }

    public static ReferenceType fromPath(String path) {
        return Arrays.stream(values())
                .filter(type -> type.path.equalsIgnoreCase(path))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de catálogo no válido: " + path));
    }
}
