package com.vehicles.service.infrastructure.messaging.config;

import com.vehicles.service.infrastructure.messaging.constants.RabbitMQPatterns;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración centralizada de RabbitMQ para el vehicle-service.
 * Define:
 * - Colas que consume este servicio (RPC desde form-service)
 * - Exchange y bindings
 * - Serializacion de mensajes con Jackson
 */
@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${app.rabbitmq.vehicle-queue:vehicle-service-queue}")
    private String vehicleQueue;

    @Value("${app.rabbitmq.exchange:vehicles.exchange}")
    private String vehiclesExchange;

    @Value("${app.rabbitmq.event-exchange:cda.domain.events}")
    private String eventExchange;

    /**
     * Cola donde vehicle-service escucha solicitudes RPC de verificación.
     * form-service enviará mensajes con patrón "cda.vehicle.exists" a esta cola.
     */
    @Bean
    public Queue vehicleQueue() {
        return new Queue(vehicleQueue, true, false, false);
    }

    /**
     * Exchange directo para enrutar mensajes por patrón.
     * Los productores (form-service) usarán este exchange para enviar mensajes.
     */
    @Bean
    public DirectExchange vehiclesExchange() {
        return new DirectExchange(vehiclesExchange, true, false);
    }

    /**
     * Binding que conecta la cola del vehicle-service al exchange
     * con la routing key del patrón de verificación.
     */
    @Bean
    public Binding vehicleBinding(Queue vehicleQueue, DirectExchange vehiclesExchange) {
        return BindingBuilder.bind(vehicleQueue)
                .to(vehiclesExchange)
                .with(RabbitMQPatterns.VEHICLE_EXISTS_PATTERN);
    }

    /**
     * Exchange temático para eventos de dominio.
     * El tracker service (Flask) se suscribe a routing keys como vehiculo.registro.creado
     * para construir el grafo relacional en Neo4j.
     */
    @Bean
    public TopicExchange domainEventsExchange() {
        return new TopicExchange(eventExchange, true, false);
    }

    /**
     * Convertidor de mensajes JSON usando Jackson.
     * Permite enviar/recibir DTOs como JSON en lugar de serialización Java binaria.
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
