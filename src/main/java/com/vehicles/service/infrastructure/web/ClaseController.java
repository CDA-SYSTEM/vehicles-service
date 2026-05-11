package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.service.ClaseService;
import com.vehicles.service.domain.model.Clase;
import com.vehicles.service.infrastructure.web.dto.ClaseRequest;
import com.vehicles.service.infrastructure.web.dto.ClaseResponse;
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

@Tag(name = "Clases", description = "CRUD del catálogo de clases de vehículo")
@RestController
@RequestMapping("/clase")
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class ClaseController {

    private final ClaseService claseService;

    @Operation(summary = "Crear clase", description = "Registra una nueva clase. Responde 201 con la entidad creada.")
    @PostMapping
    public ResponseEntity<ClaseResponse> create(@Valid @RequestBody ClaseRequest request) {
        Clase created = claseService.create(request.toDomain());
        return ResponseEntity
                .created(URI.create("/clase/" + created.getId()))
                .body(ClaseResponse.from(created));
    }

    @Operation(summary = "Listar clases", description = "Devuelve todas las clases registradas.")
    @GetMapping
    public ResponseEntity<List<ClaseResponse>> list() {
        List<ClaseResponse> result = claseService.findAll().stream()
                .map(ClaseResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener clase por id", description = "Consulta una clase por su identificador. 404 si no existe.")
    @GetMapping("/{id}")
    public ResponseEntity<ClaseResponse> getById(@PathVariable Long id) {
        return claseService.findById(id)
                .map(clase -> ResponseEntity.ok(ClaseResponse.from(clase)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar clase", description = "Actualiza los datos de la clase indicada.")
    @PutMapping("/{id}")
    public ResponseEntity<ClaseResponse> update(@PathVariable Long id, @Valid @RequestBody ClaseRequest request) {
        Clase updated = claseService.update(id, request.toDomain());
        return ResponseEntity.ok(ClaseResponse.from(updated));
    }

    @Operation(summary = "Eliminar clase", description = "Elimina la clase por id. Respuesta 204 sin cuerpo.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        claseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}