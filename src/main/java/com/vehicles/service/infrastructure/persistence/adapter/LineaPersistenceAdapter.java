package com.vehicles.service.infrastructure.persistence.adapter;

import com.vehicles.service.application.port.out.LineaPersistencePort;
import com.vehicles.service.domain.model.Linea;
import com.vehicles.service.infrastructure.persistence.entity.LineaEntity;
import com.vehicles.service.infrastructure.persistence.repository.LineaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LineaPersistenceAdapter implements LineaPersistencePort {

    private final LineaJpaRepository lineaJpaRepository;

    @Override
    @Transactional
    public Linea save(Linea linea) {
        LineaEntity entity = new LineaEntity(linea.getId(), linea.getNombre());
        LineaEntity saved = lineaJpaRepository.save(entity);
        return new Linea(saved.getId(), saved.getNombre());
    }

    @Override
    public Optional<Linea> findById(Long id) {
        return lineaJpaRepository.findById(id)
                .map(entity -> new Linea(entity.getId(), entity.getNombre()));
    }

    @Override
    public List<Linea> findAll() {
        return lineaJpaRepository.findAll().stream()
                .map(entity -> new Linea(entity.getId(), entity.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        lineaJpaRepository.deleteById(id);
    }
}