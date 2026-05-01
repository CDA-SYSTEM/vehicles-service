package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.service.LineaService;
import com.vehicles.service.domain.model.Linea;
import com.vehicles.service.infrastructure.web.dto.LineaRequest;
import com.vehicles.service.infrastructure.web.dto.LineaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/linea")
@RequiredArgsConstructor
public class LineaController {

    private final LineaService lineaService;

    @PostMapping
    public ResponseEntity<LineaResponse> create(@Valid @RequestBody LineaRequest request) {
        Linea created = lineaService.create(request.toDomain());
        return ResponseEntity
                .created(URI.create("/linea/" + created.getId()))
                .body(LineaResponse.from(created));
    }

    @GetMapping
    public ResponseEntity<List<LineaResponse>> list() {
        List<LineaResponse> result = lineaService.findAll().stream()
                .map(LineaResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineaResponse> getById(@PathVariable Long id) {
        return lineaService.findById(id)
                .map(linea -> ResponseEntity.ok(LineaResponse.from(linea)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<LineaResponse> update(@PathVariable Long id, @Valid @RequestBody LineaRequest request) {
        Linea updated = lineaService.update(id, request.toDomain());
        return ResponseEntity.ok(LineaResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        lineaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}