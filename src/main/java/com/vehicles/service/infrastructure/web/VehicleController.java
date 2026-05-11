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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springdoc.core.annotations.ParameterObject;

@Tag(name = "Vehicle", description = "Gestión de vehículos")
@RestController
@RequestMapping("/vehiculo")
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class VehicleController {

    private final VehicleUseCase vehicleUseCase;

    @Operation(summary = "Crear un vehículo", description = "Crea un nuevo vehículo en el sistema.")
    @PostMapping
    public ResponseEntity<VehicleResponseDto> createVehicle(@Valid @RequestBody CreateVehicleRequest request) {
        var response = vehicleUseCase.createVehicle(request.toCommand());
        return ResponseEntity
                .created(URI.create("/vehiculo/" + response.id()))
                .body(VehicleResponseDto.from(response));
    }

    @Operation(summary = "Obtener un vehículo", description = "Obtiene un vehículo por su ID, con catálogos relacionados (id y nombre).")
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> getVehicleById(
            @Parameter(description = "Identificador numérico del vehículo", required = true)
            @PathVariable Long id) {
        return vehicleUseCase.findVehicleById(id)
                .map(VehicleResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Listar vehículos",
            description = "Lista vehículos con paginación Spring (`page`, `size`, `sort`). "
                    + "Opcionalmente filtra por query `clienteId`.")
    @GetMapping
    public ResponseEntity<Page<VehicleResponseDto>> listVehicles(
            @Parameter(description = "Si se envía, solo vehículos de ese cliente")
            @RequestParam(required = false) String clienteId,
            @ParameterObject
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        Page<VehicleResponseDto> vehicles = vehicleUseCase.findVehicles(clienteId, pageable)
                .map(VehicleResponseDto::from);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(
            summary = "Listar vehículos por cliente",
            description = "Equivalente a listar con `clienteId` fijado en la ruta. Paginación: `page`, `size`, `sort`.")
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<Page<VehicleResponseDto>> listVehiclesByClienteId(
            @Parameter(description = "Identificador del cliente", example = "CLI-001", required = true)
            @PathVariable String clienteId,
            @ParameterObject
            @PageableDefault(size = 20, page = 0) Pageable pageable) {
        Page<VehicleResponseDto> vehicles = vehicleUseCase.findVehicles(clienteId, pageable)
                .map(VehicleResponseDto::from);
        return ResponseEntity.ok(vehicles);
    }

    @Operation(summary = "Actualizar un vehículo", description = "Actualiza los datos de un vehículo existente.")
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> updateVehicle(
            @Parameter(description = "Identificador numérico del vehículo", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UpdateVehicleRequest request) {
        return vehicleUseCase.updateVehicle(id, request.toCommand())
                .map(VehicleResponseDto::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un vehículo", description = "Elimina un vehículo por su ID. Respuesta 204 sin cuerpo.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(
            @Parameter(description = "Identificador numérico del vehículo", required = true)
            @PathVariable Long id) {
        vehicleUseCase.deleteVehicle(id);
        return ResponseEntity.noContent().build();
    }
}
