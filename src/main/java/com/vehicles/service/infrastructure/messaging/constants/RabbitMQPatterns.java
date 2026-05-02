package com.vehicles.service.infrastructure.messaging.constants;

public class RabbitMQPatterns {
    private RabbitMQPatterns() {
        throw new AssertionError("No instances of RabbitMQPatterns");
    }

    /**
     * Patrón RPC que form-service invoca para verificar si existe un vehículo por ID
     */
    public static final String VEHICLE_EXISTS_PATTERN = "cda.vehicle.exists";

    /**
     * Exchange de routing para mensajería
     */
    public static final String VEHICLES_EXCHANGE = "vehicles.exchange";

    /**
     * Cola específica del vehicle-service para escuchar requests RPC
     */
    public static final String VEHICLES_QUEUE_PROPERTY_KEY = "app.rabbitmq.vehicle-queue";
}
