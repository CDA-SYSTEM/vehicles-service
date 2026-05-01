package com.vehicles.service.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
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
        }
        if (dbUser != null) {
            properties.put("spring.datasource.username", dbUser);
        }
        if (dbPassword != null) {
            properties.put("spring.datasource.password", dbPassword);
        }

        if (!properties.isEmpty()) {
            environment.getPropertySources().addFirst(new MapPropertySource("dotenv", properties));
        }
    }
}