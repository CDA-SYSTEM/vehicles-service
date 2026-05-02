package com.vehicles.service.application.service;

import com.vehicles.service.application.port.out.ClasePersistencePort;
import com.vehicles.service.domain.model.Clase;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class ClaseService {

    private final ClasePersistencePort persistencePort;

    public Clase create(Clase clase) {
        return persistencePort.save(clase);
    }

    public Optional<Clase> findById(Long id) {
        return persistencePort.findById(id);
    }

    public List<Clase> findAll() {
        return persistencePort.findAll();
    }

    public Clase update(Long id, Clase clase) {
        persistencePort.findById(id)
                .orElseThrow(() -> new IllegalStateException("No existe la clase con id=" + id));
        Clase updated = new Clase(id, clase.getNombre());
        return persistencePort.save(updated);
    }

    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}