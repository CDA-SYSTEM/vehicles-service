package com.vehicles.service.application.service;

import com.vehicles.service.application.port.out.LineaPersistencePort;
import com.vehicles.service.domain.model.Linea;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
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
        Linea current = persistencePort.findById(id)
                .orElseThrow(() -> new IllegalStateException("No existe la línea con id=" + id));
        Linea updated = new Linea(id, linea.getNombre());
        return persistencePort.save(updated);
    }

    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}