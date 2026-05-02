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

@RestController
@RequestMapping("/clase")
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class ClaseController {

    private final ClaseService claseService;

    @PostMapping
    public ResponseEntity<ClaseResponse> create(@Valid @RequestBody ClaseRequest request) {
        Clase created = claseService.create(request.toDomain());
        return ResponseEntity
                .created(URI.create("/clase/" + created.getId()))
                .body(ClaseResponse.from(created));
    }

    @GetMapping
    public ResponseEntity<List<ClaseResponse>> list() {
        List<ClaseResponse> result = claseService.findAll().stream()
                .map(ClaseResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClaseResponse> getById(@PathVariable Long id) {
        return claseService.findById(id)
                .map(clase -> ResponseEntity.ok(ClaseResponse.from(clase)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClaseResponse> update(@PathVariable Long id, @Valid @RequestBody ClaseRequest request) {
        Clase updated = claseService.update(id, request.toDomain());
        return ResponseEntity.ok(ClaseResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        claseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}