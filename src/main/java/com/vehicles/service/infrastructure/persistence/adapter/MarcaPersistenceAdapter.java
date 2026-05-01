package com.vehicles.service.infrastructure.persistence.adapter;

import com.vehicles.service.application.port.out.MarcaPersistencePort;
import com.vehicles.service.domain.model.Marca;
import com.vehicles.service.infrastructure.persistence.entity.MarcaEntity;
import com.vehicles.service.infrastructure.persistence.repository.MarcaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MarcaPersistenceAdapter implements MarcaPersistencePort {

    private final MarcaJpaRepository marcaJpaRepository;

    @Override
    @Transactional
    public Marca save(Marca marca) {
        MarcaEntity entity = new MarcaEntity(marca.getId(), marca.getNombre());
        MarcaEntity saved = marcaJpaRepository.save(entity);
        return new Marca(saved.getId(), saved.getNombre());
    }

    @Override
    public Optional<Marca> findById(Long id) {
        return marcaJpaRepository.findById(id)
                .map(entity -> new Marca(entity.getId(), entity.getNombre()));
    }

    @Override
    public List<Marca> findAll() {
        return marcaJpaRepository.findAll().stream()
                .map(entity -> new Marca(entity.getId(), entity.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        marcaJpaRepository.deleteById(id);
    }
}