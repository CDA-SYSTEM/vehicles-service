package com.vehicles.service.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DotenvLoader implements EnvironmentPostProcessor {

    private static final String SPRING_PREFIX = "SPRING_";
    private static final String APP_PREFIX = "APP_";
    private static final String SERVER_PREFIX = "SERVER_";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        Map<String, Object> properties = new HashMap<>();

        // 1. Mapeo explícito para claves cortas (formato legacy: DB_URL, RABBITMQ_HOST, API_KEY)
        mapShortKeys(dotenv, properties);

        // 2. Mapeo automático para claves con prefijo SPRING_ o SERVER_
        //    SPRING_DATASOURCE_URL → spring.datasource.url
        //    SERVER_PORT → server.port
        dotenv.entries().forEach(entry -> {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.startsWith(SPRING_PREFIX) || key.startsWith(SERVER_PREFIX)) {
                properties.put(toDottedKey(key), value);
            }
        });

        // 3. Mapeo explícito para claves APP_ (porque pueden contener guiones en el nombre Spring)
        mapAppKeys(dotenv, properties);

        if (!properties.isEmpty()) {
            environment.getPropertySources().addFirst(new MapPropertySource("dotenv", properties));
        }
    }

    private void mapAppKeys(Dotenv dotenv, Map<String, Object> properties) {
        String exchange = dotenv.get("APP_RABBITMQ_EXCHANGE");
        String eventExchange = dotenv.get("APP_RABBITMQ_EVENT_EXCHANGE");
        String routingKeyVehiculo = dotenv.get("APP_RABBITMQ_ROUTING_KEY_VEHICULO");
        String vehicleQueue = dotenv.get("APP_RABBITMQ_VEHICLE_QUEUE");
        String rpcTimeout = dotenv.get("APP_RABBITMQ_RPC_TIMEOUT_MS");

        if (exchange != null) properties.put("app.rabbitmq.exchange", exchange);
        if (eventExchange != null) properties.put("app.rabbitmq.event-exchange", eventExchange);
        if (routingKeyVehiculo != null) properties.put("app.rabbitmq.routing-key.vehiculo", routingKeyVehiculo);
        if (vehicleQueue != null) properties.put("app.rabbitmq.vehicle-queue", vehicleQueue);
        if (rpcTimeout != null) properties.put("app.rabbitmq.rpc-timeout-ms", rpcTimeout);
    }

    private void mapShortKeys(Dotenv dotenv, Map<String, Object> properties) {
        String dbUrl = dotenv.get("DB_URL");
        String dbUser = dotenv.get("DB_USER");
        String dbPassword = dotenv.get("DB_PASSWORD");

        if (dbUrl != null) {
            properties.put("spring.datasource.url", dbUrl);
            properties.put("spring.datasource.driver-class-name", "org.postgresql.Driver");
        }
        if (dbUser != null) {
            properties.put("spring.datasource.username", dbUser);
        }
        if (dbPassword != null) {
            properties.put("spring.datasource.password", dbPassword);
        }

        String rmqHost = dotenv.get("RABBITMQ_HOST");
        String rmqPort = dotenv.get("RABBITMQ_PORT");
        String rmqUser = dotenv.get("RABBITMQ_QUEUE_USER");
        String rmqPassword = dotenv.get("RABBITMQ_PASSWORD");
        String rmqVhost = dotenv.get("RABBITMQ_VHOST");
        String rmqQueue = dotenv.get("RABBITMQ_VEHICLE_QUEUE");
        String rmqTimeout = dotenv.get("RABBITMQ_RPC_TIMEOUT_MS");

        if (rmqHost != null) properties.put("spring.rabbitmq.host", rmqHost);
        if (rmqPort != null) properties.put("spring.rabbitmq.port", rmqPort);
        if (rmqUser != null) properties.put("spring.rabbitmq.username", rmqUser);
        if (rmqPassword != null) properties.put("spring.rabbitmq.password", rmqPassword);
        if (rmqVhost != null) properties.put("spring.rabbitmq.virtual-host", rmqVhost);
        if (rmqQueue != null) properties.put("app.rabbitmq.vehicle-queue", rmqQueue);
        if (rmqTimeout != null) properties.put("app.rabbitmq.rpc-timeout-ms", rmqTimeout);

        String apiKey = dotenv.get("API_KEY");
        if (apiKey != null) properties.put("app.security.api-key", apiKey);
    }

    /**
     * Convierte una clave con prefijo SPRING_, APP_ o SERVER_ a notación Spring dotted.
     * Ej: SPRING_DATASOURCE_URL → spring.datasource.url
     *     APP_RABBITMQ_EXCHANGE → app.rabbitmq.exchange
     *     SERVER_PORT → server.port
     */
    private String toDottedKey(String key) {
        String[] parts = key.split("_");
        if (parts.length == 0) return key.toLowerCase(Locale.ROOT);
        String prefix = parts[0].toLowerCase(Locale.ROOT);
        String rest = Stream.of(parts)
                .skip(1)
                .map(p -> p.toLowerCase(Locale.ROOT))
                .collect(Collectors.joining("."));
        return rest.isEmpty() ? prefix : prefix + "." + rest;
    }
}