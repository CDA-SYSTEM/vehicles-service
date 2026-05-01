package com.vehicles.service.infrastructure.persistence.adapter;

import com.vehicles.service.application.port.out.ClasePersistencePort;
import com.vehicles.service.domain.model.Clase;
import com.vehicles.service.infrastructure.persistence.entity.ClaseEntity;
import com.vehicles.service.infrastructure.persistence.repository.ClaseJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ClasePersistenceAdapter implements ClasePersistencePort {

    private final ClaseJpaRepository claseJpaRepository;

    @Override
    @Transactional
    public Clase save(Clase clase) {
        ClaseEntity entity = new ClaseEntity(clase.getId(), clase.getNombre());
        ClaseEntity saved = claseJpaRepository.save(entity);
        return new Clase(saved.getId(), saved.getNombre());
    }

    @Override
    public Optional<Clase> findById(Long id) {
        return claseJpaRepository.findById(id)
                .map(entity -> new Clase(entity.getId(), entity.getNombre()));
    }

    @Override
    public List<Clase> findAll() {
        return claseJpaRepository.findAll().stream()
                .map(entity -> new Clase(entity.getId(), entity.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        claseJpaRepository.deleteById(id);
    }
}