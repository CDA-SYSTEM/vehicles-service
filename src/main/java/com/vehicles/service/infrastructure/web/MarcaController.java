package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.service.MarcaService;
import com.vehicles.service.domain.model.Marca;
import com.vehicles.service.infrastructure.web.dto.MarcaRequest;
import com.vehicles.service.infrastructure.web.dto.MarcaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/marca")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @PostMapping
    public ResponseEntity<MarcaResponse> create(@Valid @RequestBody MarcaRequest request) {
        Marca created = marcaService.create(request.toDomain());
        return ResponseEntity
                .created(URI.create("/marca/" + created.getId()))
                .body(MarcaResponse.from(created));
    }

    @GetMapping
    public ResponseEntity<List<MarcaResponse>> list() {
        List<MarcaResponse> result = marcaService.findAll().stream()
                .map(MarcaResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponse> getById(@PathVariable Long id) {
        return marcaService.findById(id)
                .map(marca -> ResponseEntity.ok(MarcaResponse.from(marca)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponse> update(@PathVariable Long id, @Valid @RequestBody MarcaRequest request) {
        Marca updated = marcaService.update(id, request.toDomain());
        return ResponseEntity.ok(MarcaResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        marcaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}