package com.vehicles.service.infrastructure.messaging.publisher;

import com.vehicles.service.infrastructure.messaging.constants.RabbitMQPatterns;
import com.vehicles.service.infrastructure.messaging.dto.VehicleCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class VehicleEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishVehicleCreated(VehicleCreatedEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQPatterns.VEHICLES_EXCHANGE,
                    RabbitMQPatterns.VEHICLE_CREATED_PATTERN,
                    event
            );
            log.info("Evento publicado: routingKey={}, placa={}",
                    RabbitMQPatterns.VEHICLE_CREATED_PATTERN, event.placa());
        } catch (Exception e) {
            log.error("Error al publicar evento vehiculo.registro.creado para placa={}", event.placa(), e);
        }
    }
}
