package com.vehicles.service.infrastructure.messaging.listener;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vehicles.service.application.port.out.VehiclePersistencePort;
import com.vehicles.service.infrastructure.messaging.constants.RabbitMQPatterns;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * RPC listener compatible con NestJS ClientProxy.send / @MessagePattern sobre RabbitMQ.
 * 
 * Formato de request (desde form-service NestJS):
 * {
 *   "pattern": "cda.vehicle.exists",
 *   "data": { "id": "123" },
 *   "id": "correlationId"
 * }
 * 
 * Formato de response (hacia form-service):
 * {
 *   "id": "correlationId",
 *   "response": { "exists": true },
 *   "err": null
 * }
 */
@Slf4j
@Component
public class VehicleExistsRpcListener {

    private final RabbitTemplate rabbitTemplate;
    private final VehiclePersistencePort vehiclePersistencePort;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public VehicleExistsRpcListener(RabbitTemplate rabbitTemplate, VehiclePersistencePort vehiclePersistencePort) {
        this.rabbitTemplate = rabbitTemplate;
        this.vehiclePersistencePort = vehiclePersistencePort;
    }

    /**
     * Escucha mensajes RPC en la cola del vehicle-service.
     * Procesa el patrón "cda.vehicle.exists" y responde síncronamente.
     */
    @RabbitListener(queues = "${app.rabbitmq.vehicle-queue}", concurrency = "1-4")
    public void onMessage(Message message) throws Exception {
        var props = message.getMessageProperties();
        String replyTo = props.getReplyTo();
        
        // Si no hay cola de respuesta, no hacemos nada
        if (replyTo == null || replyTo.isBlank()) {
            log.warn("Mensaje recibido sin replyTo, descartado");
            return;
        }

        try {
            // Parsear el cuerpo del mensaje como JSON
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            log.debug("Mensaje recibido: {}", body);
            
            JsonNode root = objectMapper.readTree(body);
            
            // Extraer el correlation ID (del mensaje o de las propiedades)
            String nestCorrelationId = root.path("id").asText(null);
            if (nestCorrelationId == null || nestCorrelationId.isBlank()) {
                nestCorrelationId = props.getCorrelationId();
            }

            // Extraer el patrón
            String pattern = root.path("pattern").asText("");
            
            // Construir el envelope de respuesta
            ObjectNode envelope = objectMapper.createObjectNode();
            envelope.put("id", nestCorrelationId != null ? nestCorrelationId : "");

            // Validar patrón
            if (!RabbitMQPatterns.VEHICLE_EXISTS_PATTERN.equals(pattern)) {
                log.warn("Patrón desconocido: {}", pattern);
                envelope.putNull("response");
                envelope.put("err", "UNKNOWN_PATTERN: " + pattern);
            } else {
                // Procesar la solicitud de verificación
                boolean exists = resolveVehicleExists(root.path("data"));
                ObjectNode response = objectMapper.createObjectNode();
                response.put("exists", exists);
                envelope.set("response", response);
                envelope.putNull("err");
                
                log.info("Vehicle exists check result: {}", exists);
            }

            // Enviar la respuesta a la cola replyTo
            byte[] responseBody = objectMapper.writeValueAsBytes(envelope);
            rabbitTemplate.send(
                    "",
                    replyTo,
                    MessageBuilder.withBody(responseBody)
                            .setContentType("application/json")
                            .setCorrelationId(props.getCorrelationId())
                            .build()
            );
            
        } catch (Exception e) {
            log.error("Error procesando mensaje RPC", e);
            // Intentar enviar error response
            try {
                ObjectNode errorEnvelope = objectMapper.createObjectNode();
                errorEnvelope.put("id", props.getCorrelationId() != null ? props.getCorrelationId() : "");
                errorEnvelope.putNull("response");
                errorEnvelope.put("err", "ERROR: " + e.getMessage());
                
                byte[] errorBody = objectMapper.writeValueAsBytes(errorEnvelope);
                rabbitTemplate.send(
                        "",
                        replyTo,
                        MessageBuilder.withBody(errorBody)
                                .setContentType("application/json")
                                .setCorrelationId(props.getCorrelationId())
                                .build()
                );
            } catch (Exception innerE) {
                log.error("Error enviando error response", innerE);
            }
        }
    }

    /**
     * Resuelve si el vehículo existe basado en el ID extraído del JSON.
     * 
     * @param data JsonNode con estructura { "id": "123" }
     * @return true si el vehículo existe, false en caso contrario
     */
    private boolean resolveVehicleExists(JsonNode data) {
        if (data == null || data.isNull()) {
            log.warn("Data nula en solicitud");
            return false;
        }

        JsonNode idNode = data.get("id");
        if (idNode == null || idNode.isNull()) {
            log.warn("ID no encontrado en data");
            return false;
        }

        String idStr = idNode.isNumber() ? idNode.asText() : idNode.asText("");
        if (idStr.isBlank()) {
            log.warn("ID está vacío");
            return false;
        }

        try {
            // Asumir que el ID es Long (ajustar si es diferente)
            long vehicleId = Long.parseLong(idStr.trim());
            boolean exists = vehiclePersistencePort.findById(vehicleId).isPresent();
            log.info("Vehiculo ID {} existe: {}", vehicleId, exists);
            return exists;
        } catch (NumberFormatException e) {
            log.warn("ID no es válido (no es numerico): {}", idStr);
            return false;
        }
    }
}
