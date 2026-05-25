package com.vehicles.service.infrastructure.messaging.publisher;

import com.vehicles.service.infrastructure.messaging.dto.VehiculoRegistradoEvent;
import com.vehicles.service.domain.model.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class VehicleEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public VehicleEventPublisher(
            RabbitTemplate rabbitTemplate,
            @Value("${app.rabbitmq.event-exchange:cda.domain.events}") String exchange,
            @Value("${app.rabbitmq.routing-key.vehiculo:vehiculo.registro.creado}") String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    public void publicarVehiculoCreado(Vehicle vehiculo, String propietarioId) {
        Integer modelo;
        try {
            modelo = Integer.valueOf(vehiculo.modelo());
        } catch (NumberFormatException e) {
            log.warn("modelo '{}' no es numérico, se envía como 0", vehiculo.modelo());
            modelo = 0;
        }

        VehiculoRegistradoEvent evento = VehiculoRegistradoEvent.from(
                vehiculo.placa(),
                vehiculo.marcaNombre(),
                modelo,
                vehiculo.tipoVehiculoNombre(),
                propietarioId
        );

        try {
            rabbitTemplate.convertAndSend(exchange, routingKey, evento);
            log.info("Evento publicado: exchange={}, routingKey={}, placa={}",
                    exchange, routingKey, vehiculo.placa());
        } catch (Exception e) {
            log.error("Error al publicar evento {} para placa={}",
                    routingKey, vehiculo.placa(), e);
        }
    }
}
