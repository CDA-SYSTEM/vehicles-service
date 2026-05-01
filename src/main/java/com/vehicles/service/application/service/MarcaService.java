package com.vehicles.service.application.service;

import com.vehicles.service.application.port.out.MarcaPersistencePort;
import com.vehicles.service.domain.model.Marca;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaPersistencePort persistencePort;

    public Marca create(Marca marca) {
        return persistencePort.save(marca);
    }

    public Optional<Marca> findById(Long id) {
        return persistencePort.findById(id);
    }

    public List<Marca> findAll() {
        return persistencePort.findAll();
    }

    public Marca update(Long id, Marca marca) {
        Marca current = persistencePort.findById(id)
                .orElseThrow(() -> new IllegalStateException("No existe la marca con id=" + id));
        Marca updated = new Marca(id, marca.getNombre());
        return persistencePort.save(updated);
    }

    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}