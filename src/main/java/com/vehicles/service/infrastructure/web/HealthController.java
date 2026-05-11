package com.vehicles.service.infrastructure.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Salud", description = "Comprobación de disponibilidad del servicio (API v1)")
@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @Operation(
            summary = "Health check",
            description = "Indica si el servicio está arriba. Útil para balanceadores, Kubernetes o API Gateway.")
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "timestamp", LocalDateTime.now().toString(),
                "service", "vehicles-service"
        ));
    }
}