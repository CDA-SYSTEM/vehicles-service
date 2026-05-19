package com.vehicles.service.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DotenvLoader implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        Map<String, Object> properties = new HashMap<>();

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

        if (!properties.isEmpty()) {
            environment.getPropertySources().addFirst(new MapPropertySource("dotenv", properties));
        }
    }
}