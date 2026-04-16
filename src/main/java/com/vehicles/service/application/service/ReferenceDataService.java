package com.vehicles.service.application.service;

import com.vehicles.service.application.port.in.ReferenceDataUseCase;
import com.vehicles.service.application.port.out.ReferenceDataPersistencePort;
import com.vehicles.service.domain.model.reference.ReferenceData;
import com.vehicles.service.domain.model.reference.ReferenceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReferenceDataService implements ReferenceDataUseCase {

    private final ReferenceDataPersistencePort persistencePort;

    @Override
    public ReferenceData create(ReferenceType type, ReferenceData data) {
        return persistencePort.save(type, data);
    }

    @Override
    public Optional<ReferenceData> findById(ReferenceType type, Long id) {
        return persistencePort.findById(type, id);
    }

    @Override
    public List<ReferenceData> findAll(ReferenceType type) {
        return persistencePort.findAll(type);
    }

    @Override
    public ReferenceData update(ReferenceType type, Long id, ReferenceData data) {
        ReferenceData current = persistencePort.findById(type, id)
                .orElseThrow(() -> new IllegalStateException("No existe el registro de referencia con id=" + id));
        ReferenceData updated = new ReferenceData(id, data.nombre(), data.descripcion());
        return persistencePort.save(type, updated);
    }

    @Override
    public void deleteById(ReferenceType type, Long id) {
        persistencePort.deleteById(type, id);
    }
}
