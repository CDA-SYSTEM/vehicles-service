package com.vehicles.service.application.port.in;

import com.vehicles.service.domain.model.reference.ReferenceData;
import com.vehicles.service.domain.model.reference.ReferenceType;

import java.util.List;
import java.util.Optional;

public interface ReferenceDataUseCase {

    ReferenceData create(ReferenceType type, ReferenceData data);

    Optional<ReferenceData> findById(ReferenceType type, Long id);

    List<ReferenceData> findAll(ReferenceType type);

    ReferenceData update(ReferenceType type, Long id, ReferenceData data);

    void deleteById(ReferenceType type, Long id);
}
