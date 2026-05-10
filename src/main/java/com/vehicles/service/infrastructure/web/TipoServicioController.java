package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.service.TipoServicioService;
import com.vehicles.service.domain.model.TipoServicio;
import com.vehicles.service.infrastructure.web.dto.TipoServicioRequest;
import com.vehicles.service.infrastructure.web.dto.TipoServicioResponse;
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

@Tag(name = "Tipo Servicio", description = "Gestión de tipos de servicios")
@RestController
@RequestMapping("/tipo-servicio")
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class TipoServicioController {

    private final TipoServicioService tipoServicioService;

    @Operation(summary = "Crear un tipo de servicio", description = "Crea un nuevo tipo de servicio en el sistema.")
    @PostMapping
    public ResponseEntity<TipoServicioResponse> create(@Valid @RequestBody TipoServicioRequest request) {
        TipoServicio created = tipoServicioService.create(request.toDomain());
        return ResponseEntity
                .created(URI.create("/tipo-servicio/" + created.getId()))
                .body(TipoServicioResponse.from(created));
    }

    @Operation(summary = "Listar tipos de servicios", description = "Lista todos los tipos de servicios disponibles.")
    @GetMapping
    public ResponseEntity<List<TipoServicioResponse>> list() {
        List<TipoServicioResponse> result = tipoServicioService.findAll().stream()
                .map(TipoServicioResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener un tipo de servicio", description = "Obtiene un tipo de servicio por su ID.")
    @GetMapping("/{id}")
    public ResponseEntity<TipoServicioResponse> getById(@PathVariable Long id) {
        return tipoServicioService.findById(id)
                .map(tipoServicio -> ResponseEntity.ok(TipoServicioResponse.from(tipoServicio)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar un tipo de servicio", description = "Actualiza los datos de un tipo de servicio existente.")
    @PutMapping("/{id}")
    public ResponseEntity<TipoServicioResponse> update(@PathVariable Long id, @Valid @RequestBody TipoServicioRequest request) {
        TipoServicio updated = tipoServicioService.update(id, request.toDomain());
        return ResponseEntity.ok(TipoServicioResponse.from(updated));
    }

    @Operation(summary = "Eliminar un tipo de servicio", description = "Elimina un tipo de servicio por su ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoServicioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}