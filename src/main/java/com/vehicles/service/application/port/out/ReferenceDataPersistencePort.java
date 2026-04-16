package com.vehicles.service.application.port.out;

import com.vehicles.service.domain.model.reference.ReferenceData;
import com.vehicles.service.domain.model.reference.ReferenceType;

import java.util.List;
import java.util.Optional;

public interface ReferenceDataPersistencePort {

    ReferenceData save(ReferenceType type, ReferenceData data);

    Optional<ReferenceData> findById(ReferenceType type, Long id);

    List<ReferenceData> findAll(ReferenceType type);

    void deleteById(ReferenceType type, Long id);
}
