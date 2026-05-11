package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.service.MarcaService;
import com.vehicles.service.domain.model.Marca;
import com.vehicles.service.infrastructure.web.dto.MarcaRequest;
import com.vehicles.service.infrastructure.web.dto.MarcaResponse;
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

@Tag(name = "Marcas", description = "CRUD del catálogo de marcas de vehículo")
@RestController
@RequestMapping("/marca")
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class MarcaController {

    private final MarcaService marcaService;

    @Operation(summary = "Crear marca", description = "Registra una nueva marca. Responde 201 con la entidad creada.")
    @PostMapping
    public ResponseEntity<MarcaResponse> create(@Valid @RequestBody MarcaRequest request) {
        Marca created = marcaService.create(request.toDomain());
        return ResponseEntity
                .created(URI.create("/marca/" + created.getId()))
                .body(MarcaResponse.from(created));
    }

    @Operation(summary = "Listar marcas", description = "Devuelve todas las marcas registradas.")
    @GetMapping
    public ResponseEntity<List<MarcaResponse>> list() {
        List<MarcaResponse> result = marcaService.findAll().stream()
                .map(MarcaResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener marca por id", description = "Consulta una marca por su identificador numérico. 404 si no existe.")
    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponse> getById(@PathVariable Long id) {
        return marcaService.findById(id)
                .map(marca -> ResponseEntity.ok(MarcaResponse.from(marca)))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar marca", description = "Actualiza el nombre u otros datos de la marca indicada.")
    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponse> update(@PathVariable Long id, @Valid @RequestBody MarcaRequest request) {
        Marca updated = marcaService.update(id, request.toDomain());
        return ResponseEntity.ok(MarcaResponse.from(updated));
    }

    @Operation(summary = "Eliminar marca", description = "Elimina la marca por id. Respuesta 204 sin cuerpo.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        marcaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}