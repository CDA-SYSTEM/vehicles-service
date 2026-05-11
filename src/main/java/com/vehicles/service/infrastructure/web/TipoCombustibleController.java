package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.service.TipoCombustibleService;
import com.vehicles.service.domain.model.TipoCombustible;
import com.vehicles.service.infrastructure.web.dto.TipoCombustibleRequest;
import com.vehicles.service.infrastructure.web.dto.TipoCombustibleResponse;
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

@Tag(name = "Tipos de combustible", description = "CRUD del catálogo de tipos de combustible")
@RestController
@RequestMapping("/tipo-combustible")
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class TipoCombustibleController {

    private final TipoCombustibleService tipoCombustibleService;

    @Operation(summary = "Crear tipo de combustible", description = "Registra un nuevo tipo. Responde 201 con la entidad creada.")
    @PostMapping
    public ResponseEntity<TipoCombustibleResponse> create(@Valid @RequestBody TipoCombustibleRequest request) {
        TipoCombustible created = tipoCombustibleService.create(request.toDomain());
        return ResponseEntity
                .created(URI.create("/tipo-combustible/" + created.getId()))
                .body(TipoCombustibleResponse.from(created));
    }

    @Operation(summary = "Listar tipos de combustible", description = "Devuelve todos los tipos registrados.")
    @GetMapping
    public ResponseEntity<List<TipoCombustibleResponse>> list() {
        List<TipoCombustibleResponse> result = tipoCombustibleService.findAll().stream()
                .map(TipoCombustibleResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener tipo de combustible por id", description = "Consulta por identificador. 404 si no existe.")
    @GetMapping("/{id}")
    public ResponseEntity<TipoCombustibleResponse> getById(@PathVariable Long id) {
        return tipoCombustibleService.findById(id)
                .map(tipoCombustible -> ResponseEntity.ok(TipoCombustibleResponse.from(tipoCombustible)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar tipo de combustible", description = "Actualiza los datos del registro indicado.")
    @PutMapping("/{id}")
    public ResponseEntity<TipoCombustibleResponse> update(@PathVariable Long id, @Valid @RequestBody TipoCombustibleRequest request) {
        TipoCombustible updated = tipoCombustibleService.update(id, request.toDomain());
        return ResponseEntity.ok(TipoCombustibleResponse.from(updated));
    }

    @Operation(summary = "Eliminar tipo de combustible", description = "Elimina por id. Respuesta 204 sin cuerpo.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoCombustibleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}