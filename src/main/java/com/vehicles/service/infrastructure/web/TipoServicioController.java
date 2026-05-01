package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.service.TipoServicioService;
import com.vehicles.service.domain.model.TipoServicio;
import com.vehicles.service.infrastructure.web.dto.TipoServicioRequest;
import com.vehicles.service.infrastructure.web.dto.TipoServicioResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tipo-servicio")
@RequiredArgsConstructor
public class TipoServicioController {

    private final TipoServicioService tipoServicioService;

    @PostMapping
    public ResponseEntity<TipoServicioResponse> create(@Valid @RequestBody TipoServicioRequest request) {
        TipoServicio created = tipoServicioService.create(request.toDomain());
        return ResponseEntity
                .created(URI.create("/tipo-servicio/" + created.getId()))
                .body(TipoServicioResponse.from(created));
    }

    @GetMapping
    public ResponseEntity<List<TipoServicioResponse>> list() {
        List<TipoServicioResponse> result = tipoServicioService.findAll().stream()
                .map(TipoServicioResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoServicioResponse> getById(@PathVariable Long id) {
        return tipoServicioService.findById(id)
                .map(tipoServicio -> ResponseEntity.ok(TipoServicioResponse.from(tipoServicio)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoServicioResponse> update(@PathVariable Long id, @Valid @RequestBody TipoServicioRequest request) {
        TipoServicio updated = tipoServicioService.update(id, request.toDomain());
        return ResponseEntity.ok(TipoServicioResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tipoServicioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}