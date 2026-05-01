package com.vehicles.service.infrastructure.persistence.adapter;

import com.vehicles.service.application.port.out.ColorPersistencePort;
import com.vehicles.service.domain.model.Color;
import com.vehicles.service.infrastructure.persistence.entity.ColorEntity;
import com.vehicles.service.infrastructure.persistence.repository.ColorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ColorPersistenceAdapter implements ColorPersistencePort {

    private final ColorJpaRepository colorJpaRepository;

    @Override
    @Transactional
    public Color save(Color color) {
        ColorEntity entity = new ColorEntity(color.getId(), color.getNombre());
        ColorEntity saved = colorJpaRepository.save(entity);
        return new Color(saved.getId(), saved.getNombre());
    }

    @Override
    public Optional<Color> findById(Long id) {
        return colorJpaRepository.findById(id)
                .map(entity -> new Color(entity.getId(), entity.getNombre()));
    }

    @Override
    public List<Color> findAll() {
        return colorJpaRepository.findAll().stream()
                .map(entity -> new Color(entity.getId(), entity.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        colorJpaRepository.deleteById(id);
    }
}