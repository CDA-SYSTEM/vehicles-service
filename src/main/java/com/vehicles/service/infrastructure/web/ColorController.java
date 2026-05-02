package com.vehicles.service.infrastructure.web;

import com.vehicles.service.application.service.ColorService;
import com.vehicles.service.domain.model.Color;
import com.vehicles.service.infrastructure.web.dto.ColorRequest;
import com.vehicles.service.infrastructure.web.dto.ColorResponse;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/color")
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class ColorController {

    private final ColorService colorService;

    @PostMapping
    public ResponseEntity<ColorResponse> create(@Valid @RequestBody ColorRequest request) {
        Color created = colorService.create(request.toDomain());
        return ResponseEntity
                .created(URI.create("/color/" + created.getId()))
                .body(ColorResponse.from(created));
    }

    @GetMapping
    public ResponseEntity<List<ColorResponse>> list() {
        List<ColorResponse> result = colorService.findAll().stream()
                .map(ColorResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColorResponse> getById(@PathVariable Long id) {
        return colorService.findById(id)
                .map(color -> ResponseEntity.ok(ColorResponse.from(color)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ColorResponse> update(@PathVariable Long id, @Valid @RequestBody ColorRequest request) {
        Color updated = colorService.update(id, request.toDomain());
        return ResponseEntity.ok(ColorResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        colorService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}