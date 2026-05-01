package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.service.TipoCombustibleService;
import com.vehicles.service.domain.model.TipoCombustible;
import com.vehicles.service.infrastructure.web.dto.TipoCombustibleRequest;
import com.vehicles.service.infrastructure.web.dto.TipoCombustibleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tipo-combustible")
@RequiredArgsConstructor
public class TipoCombustibleController {

    private final TipoCombustibleService tipoCombustibleService;

    @PostMapping
    public ResponseEntity<TipoCombustibleResponse> create(@Valid @RequestBody TipoCombustibleRequest request) {
        TipoCombustible created = tipoCombustibleService.create(request.toDomain());
        return ResponseEntity
                .created(URI.create("/tipo-combustible/" + created.getId()))
                .body(TipoCombustibleResponse.from(created));
    }

    @GetMapping
    public ResponseEntity<List<TipoCombustibleResponse>> list() {
        List<TipoCombustibleResponse> result = tipoCombustibleService.findAll().stream()
                .map(TipoCombustibleResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoCombustibleResponse> getById(@PathVariable Long id) {
        return tipoCombustibleService.findById(id)
                .map(tipoCombustible -> ResponseEntity.ok(TipoCombustibleResponse.from(tipoCombustible)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoCombustibleResponse> update(@PathVariable Long id, @Valid @RequestBody TipoCombustibleRequest request) {
        TipoCombustible updated = tipoCombustibleService.update(id, request.toDomain());
        return ResponseEntity.ok(TipoCombustibleResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoCombustibleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}