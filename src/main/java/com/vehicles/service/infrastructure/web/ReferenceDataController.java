package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.port.in.ReferenceDataUseCase;
import com.vehicles.service.domain.model.reference.ReferenceData;
import com.vehicles.service.domain.model.reference.ReferenceType;
import com.vehicles.service.infrastructure.web.dto.ReferenceDataRequest;
import com.vehicles.service.infrastructure.web.dto.ReferenceDataResponse;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "Catálogos unificados (v1)",
        description = """
                CRUD genérico sobre /api/v1/catalogs/{type}. El path `type` debe coincidir con un catálogo: \
                marcas, clases, lineas, colores, tipos-vehiculo, tipos-combustible, tipos-servicio.""")
@RestController
@RequestMapping("/api/v1/catalogs/{type}")
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class ReferenceDataController {

    private final ReferenceDataUseCase referenceDataUseCase;

    @Operation(summary = "Crear ítem de catálogo", description = "Crea un registro en el catálogo indicado por `type`.")
    @PostMapping
    public ResponseEntity<ReferenceDataResponse> create(
            @Parameter(
                    description = "Catálogo destino",
                    example = "marcas",
                    required = true)
            @PathVariable String type,
            @Valid @RequestBody ReferenceDataRequest request) {
        ReferenceType referenceType = ReferenceType.fromPath(type);
        ReferenceData created = referenceDataUseCase.create(referenceType, request.toDomain());
        return ResponseEntity
                .created(URI.create(String.format("/api/v1/catalogs/%s/%d", type, created.id())))
                .body(ReferenceDataResponse.from(created));
    }

    @Operation(summary = "Listar catálogo", description = "Devuelve todos los ítems del catálogo `type`.")
    @GetMapping
    public ResponseEntity<List<ReferenceDataResponse>> list(
            @Parameter(description = "Catálogo", example = "marcas", required = true)
            @PathVariable String type) {
        ReferenceType referenceType = ReferenceType.fromPath(type);
        List<ReferenceDataResponse> result = referenceDataUseCase.findAll(referenceType).stream()
                .map(ReferenceDataResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Obtener ítem por id", description = "Consulta un registro del catálogo. 404 si no existe.")
    @GetMapping("/{id}")
    public ResponseEntity<ReferenceDataResponse> findById(
            @Parameter(description = "Catálogo", example = "marcas", required = true)
            @PathVariable String type,
            @Parameter(description = "Identificador numérico del ítem", required = true)
            @PathVariable Long id) {
        ReferenceType referenceType = ReferenceType.fromPath(type);
        return referenceDataUseCase.findById(referenceType, id)
                .map(ReferenceDataResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualizar ítem", description = "Actualiza nombre u otros datos del registro en el catálogo.")
    @PutMapping("/{id}")
    public ResponseEntity<ReferenceDataResponse> update(
            @Parameter(description = "Catálogo", example = "marcas", required = true)
            @PathVariable String type,
            @Parameter(description = "Identificador numérico", required = true)
            @PathVariable Long id,
            @Valid @RequestBody ReferenceDataRequest request) {
        ReferenceType referenceType = ReferenceType.fromPath(type);
        ReferenceData updated = referenceDataUseCase.update(referenceType, id, request.toDomain());
        return ResponseEntity.ok(ReferenceDataResponse.from(updated));
    }

    @Operation(summary = "Eliminar ítem", description = "Elimina el registro del catálogo. Respuesta 204 sin cuerpo.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Catálogo", example = "marcas", required = true)
            @PathVariable String type,
            @Parameter(description = "Identificador numérico", required = true)
            @PathVariable Long id) {
        ReferenceType referenceType = ReferenceType.fromPath(type);
        referenceDataUseCase.deleteById(referenceType, id);
        return ResponseEntity.noContent().build();
    }
}
