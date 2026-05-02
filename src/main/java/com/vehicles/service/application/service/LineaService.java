package com.vehicles.service.application.service;

import com.vehicles.service.application.port.out.LineaPersistencePort;
import com.vehicles.service.domain.model.Linea;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class LineaService {

    private final LineaPersistencePort persistencePort;

    public Linea create(Linea linea) {
        return persistencePort.save(linea);
    }

    public Optional<Linea> findById(Long id) {
        return persistencePort.findById(id);
    }

    public List<Linea> findAll() {
        return persistencePort.findAll();
    }

    public Linea update(Long id, Linea linea) {
        persistencePort.findById(id)
                .orElseThrow(() -> new IllegalStateException("No existe la línea con id=" + id));
        Linea updated = new Linea(id, linea.getNombre());
        return persistencePort.save(updated);
    }

    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}