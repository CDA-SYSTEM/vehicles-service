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

@RestController
@RequestMapping("/api/v1/catalogs/{type}")
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class ReferenceDataController {

    private final ReferenceDataUseCase referenceDataUseCase;

    @PostMapping
    public ResponseEntity<ReferenceDataResponse> create(
            @PathVariable String type,
            @Valid @RequestBody ReferenceDataRequest request) {
        ReferenceType referenceType = ReferenceType.fromPath(type);
        ReferenceData created = referenceDataUseCase.create(referenceType, request.toDomain());
        return ResponseEntity
                .created(URI.create(String.format("/api/v1/catalogs/%s/%d", type, created.id())))
                .body(ReferenceDataResponse.from(created));
    }

    @GetMapping
    public ResponseEntity<List<ReferenceDataResponse>> list(@PathVariable String type) {
        ReferenceType referenceType = ReferenceType.fromPath(type);
        List<ReferenceDataResponse> result = referenceDataUseCase.findAll(referenceType).stream()
                .map(ReferenceDataResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReferenceDataResponse> findById(
            @PathVariable String type,
            @PathVariable Long id) {
        ReferenceType referenceType = ReferenceType.fromPath(type);
        return referenceDataUseCase.findById(referenceType, id)
                .map(ReferenceDataResponse::from)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReferenceDataResponse> update(
            @PathVariable String type,
            @PathVariable Long id,
            @Valid @RequestBody ReferenceDataRequest request) {
        ReferenceType referenceType = ReferenceType.fromPath(type);
        ReferenceData updated = referenceDataUseCase.update(referenceType, id, request.toDomain());
        return ResponseEntity.ok(ReferenceDataResponse.from(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable String type,
            @PathVariable Long id) {
        ReferenceType referenceType = ReferenceType.fromPath(type);
        referenceDataUseCase.deleteById(referenceType, id);
        return ResponseEntity.noContent().build();
    }
}
