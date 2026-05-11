package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.service.LineaService;
import com.vehicles.service.domain.model.Linea;
import com.vehicles.service.infrastructure.web.dto.LineaRequest;
import com.vehicles.service.infrastructure.web.dto.LineaResponse;
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

@Tag(name = "Líneas", description = "CRUD del catálogo de líneas de vehículo")
@RestController
@RequestMapping("/linea")
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class LineaController {

    private final LineaService lineaService;

    @Operation(summary = "Crear línea", description = "Registra una nueva línea. Responde 201 con la entidad creada.")
    @PostMapping
    public ResponseEntity<LineaResponse> create(@Valid @RequestBody LineaRequest request) {
        Linea created = lineaService.create(request.toDomain());
        return ResponseEntity
                .created(URI.create("/linea/" + created.getId()))
                .body(LineaResponse.from(created));
    }

    @Operation(summary = "Listar líneas", description = "Devuelve todas las líneas registradas.")
    @GetMapping
    public ResponseEntity<List<LineaResponse>> list() {
        List<LineaResponse> result = lineaService.findAll().stream()
                .map(LineaResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener línea por id", description = "Consulta una línea por su identificador. 404 si no existe.")
    @GetMapping("/{id}")
    public ResponseEntity<LineaResponse> getById(@PathVariable Long id) {
        return lineaService.findById(id)
                .map(linea -> ResponseEntity.ok(LineaResponse.from(linea)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar línea", description = "Actualiza los datos de la línea indicada.")
    @PutMapping("/{id}")
    public ResponseEntity<LineaResponse> update(@PathVariable Long id, @Valid @RequestBody LineaRequest request) {
        Linea updated = lineaService.update(id, request.toDomain());
        return ResponseEntity.ok(LineaResponse.from(updated));
    }

    @Operation(summary = "Eliminar línea", description = "Elimina la línea por id. Respuesta 204 sin cuerpo.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lineaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}