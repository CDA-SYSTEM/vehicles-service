package com.vehicles.service.application.service;

import com.vehicles.service.application.port.out.TipoServicioPersistencePort;
import com.vehicles.service.domain.model.TipoServicio;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class TipoServicioService {

    private final TipoServicioPersistencePort persistencePort;

    public TipoServicio create(TipoServicio tipoServicio) {
        return persistencePort.save(tipoServicio);
    }

    public Optional<TipoServicio> findById(Long id) {
        return persistencePort.findById(id);
    }

    public List<TipoServicio> findAll() {
        return persistencePort.findAll();
    }

    public TipoServicio update(Long id, TipoServicio tipoServicio) {
        persistencePort.findById(id)
                .orElseThrow(() -> new IllegalStateException("No existe el tipo de servicio con id=" + id));
        TipoServicio updated = new TipoServicio(id, tipoServicio.getNombre());
        return persistencePort.save(updated);
    }

    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}