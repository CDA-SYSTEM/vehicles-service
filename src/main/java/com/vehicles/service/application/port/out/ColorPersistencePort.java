package com.vehicles.service.application.port.out;

import com.vehicles.service.domain.model.Color;

import java.util.List;
import java.util.Optional;

public interface ColorPersistencePort {

    Color save(Color color);

    Optional<Color> findById(Long id);

    List<Color> findAll();

    void deleteById(Long id);
}