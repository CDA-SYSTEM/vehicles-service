package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.port.in.VehicleUseCase;
import com.vehicles.service.infrastructure.web.dto.CreateVehicleRequest;
import com.vehicles.service.infrastructure.web.dto.UpdateVehicleRequest;
import com.vehicles.service.infrastructure.web.dto.VehicleResponseDto;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/vehiculo")
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class VehicleController {

    private final VehicleUseCase vehicleUseCase;

    @PostMapping
    public ResponseEntity<VehicleResponseDto> createVehicle(@Valid @RequestBody CreateVehicleRequest request) {
        var response = vehicleUseCase.createVehicle(request.toCommand());
        return ResponseEntity
                .created(URI.create("/vehiculo/" + response.id()))
                .body(VehicleResponseDto.from(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> getVehicleById(@PathVariable Long id) {
        return vehicleUseCase.findVehicleById(id)
                .map(VehicleResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Page<VehicleResponseDto>> listVehicles(
            @RequestParam(required = false) String clienteId,
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        Page<VehicleResponseDto> vehicles = vehicleUseCase.findVehicles(clienteId, pageable)
                .map(VehicleResponseDto::from);
        return ResponseEntity.ok(vehicles);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> updateVehicle(
            @PathVariable Long id,
            @Valid @RequestBody UpdateVehicleRequest request) {
        return vehicleUseCase.updateVehicle(id, request.toCommand())
                .map(VehicleResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        vehicleUseCase.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
