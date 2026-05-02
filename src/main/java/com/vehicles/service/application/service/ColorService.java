package com.vehicles.service.application.service;

import com.vehicles.service.application.port.out.ColorPersistencePort;
import com.vehicles.service.domain.model.Color;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class ColorService {

    private final ColorPersistencePort persistencePort;

    public Color create(Color color) {
        return persistencePort.save(color);
    }

    public Optional<Color> findById(Long id) {
        return persistencePort.findById(id);
    }

    public List<Color> findAll() {
        return persistencePort.findAll();
    }

    public Color update(Long id, Color color) {
        persistencePort.findById(id)
                .orElseThrow(() -> new IllegalStateException("No existe el color con id=" + id));
        Color updated = new Color(id, color.getNombre());
        return persistencePort.save(updated);
    }

    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}