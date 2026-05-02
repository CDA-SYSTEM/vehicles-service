package com.vehicles.service.application.service;

import com.vehicles.service.application.port.out.TipoCombustiblePersistencePort;
import com.vehicles.service.domain.model.TipoCombustible;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class TipoCombustibleService {

    private final TipoCombustiblePersistencePort persistencePort;

    public TipoCombustible create(TipoCombustible tipoCombustible) {
        return persistencePort.save(tipoCombustible);
    }

    public Optional<TipoCombustible> findById(Long id) {
        return persistencePort.findById(id);
    }

    public List<TipoCombustible> findAll() {
        return persistencePort.findAll();
    }

    public TipoCombustible update(Long id, TipoCombustible tipoCombustible) {
        persistencePort.findById(id)
                .orElseThrow(() -> new IllegalStateException("No existe el tipo de combustible con id=" + id));
        TipoCombustible updated = new TipoCombustible(id, tipoCombustible.getNombre());
        return persistencePort.save(updated);
    }

    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}