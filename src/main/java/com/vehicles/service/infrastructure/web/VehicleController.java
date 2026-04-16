package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.port.in.VehicleUseCase;
import com.vehicles.service.infrastructure.web.dto.CreateVehicleRequest;
import com.vehicles.service.infrastructure.web.dto.VehicleResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {

    private final VehicleUseCase vehicleUseCase;

    @PostMapping
    public ResponseEntity<VehicleResponseDto> createVehicle(@Valid @RequestBody CreateVehicleRequest request) {
        var response = vehicleUseCase.createVehicle(request.toCommand());
        return ResponseEntity
                .created(URI.create("/api/vehicles/" + response.id()))
                .body(VehicleResponseDto.from(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> getVehicleById(@PathVariable Long id) {
        return vehicleUseCase.findVehicleById(id)
                .map(VehicleResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
