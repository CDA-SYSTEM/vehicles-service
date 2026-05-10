package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.service.TipoVehiculoService;
import com.vehicles.service.domain.model.TipoVehiculo;
import com.vehicles.service.infrastructure.web.dto.TipoVehiculoRequest;
import com.vehicles.service.infrastructure.web.dto.TipoVehiculoResponse;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Tipo Vehículo", description = "Gestión de tipos de vehículos")
@RestController
@RequestMapping("/tipo-vehiculo")
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class TipoVehiculoController {

    private final TipoVehiculoService tipoVehiculoService;

    @Operation(summary = "Crear un tipo de vehículo", description = "Crea un nuevo tipo de vehículo en el sistema.")
    @PostMapping
    public ResponseEntity<TipoVehiculoResponse> create(@Valid @RequestBody TipoVehiculoRequest request) {
        TipoVehiculo created = tipoVehiculoService.create(request.toDomain());
        return ResponseEntity
                .created(URI.create("/tipo-vehiculo/" + created.getId()))
                .body(TipoVehiculoResponse.from(created));
    }

    @Operation(summary = "Listar tipos de vehículos", description = "Lista todos los tipos de vehículos disponibles.")
    @GetMapping
    public ResponseEntity<List<TipoVehiculoResponse>> list() {
        List<TipoVehiculoResponse> result = tipoVehiculoService.findAll().stream()
                .map(TipoVehiculoResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener un tipo de vehículo", description = "Obtiene un tipo de vehículo por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<TipoVehiculoResponse> getById(@PathVariable Long id) {
        return tipoVehiculoService.findById(id)
                .map(tipoVehiculo -> ResponseEntity.ok(TipoVehiculoResponse.from(tipoVehiculo)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un tipo de vehículo", description = "Actualiza los datos de un tipo de vehículo existente.")
    @PutMapping("/{id}")
    public ResponseEntity<TipoVehiculoResponse> update(@PathVariable Long id, @Valid @RequestBody TipoVehiculoRequest request) {
        TipoVehiculo updated = tipoVehiculoService.update(id, request.toDomain());
        return ResponseEntity.ok(TipoVehiculoResponse.from(updated));
    }

    @Operation(summary = "Eliminar un tipo de vehículo", description = "Elimina un tipo de vehículo por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoVehiculoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}