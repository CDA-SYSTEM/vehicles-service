package com.vehicles.service.infrastructure.persistence.adapter;

import com.vehicles.service.application.port.out.TipoCombustiblePersistencePort;
import com.vehicles.service.domain.model.TipoCombustible;
import com.vehicles.service.infrastructure.persistence.entity.TipoCombustibleEntity;
import com.vehicles.service.infrastructure.persistence.repository.TipoCombustibleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TipoCombustiblePersistenceAdapter implements TipoCombustiblePersistencePort {

    private final TipoCombustibleJpaRepository tipoCombustibleJpaRepository;

    @Override
    @Transactional
    public TipoCombustible save(TipoCombustible tipoCombustible) {
        TipoCombustibleEntity entity = new TipoCombustibleEntity(tipoCombustible.getId(), tipoCombustible.getNombre());
        TipoCombustibleEntity saved = tipoCombustibleJpaRepository.save(entity);
        return new TipoCombustible(saved.getId(), saved.getNombre());
    }

    @Override
    public Optional<TipoCombustible> findById(Long id) {
        return tipoCombustibleJpaRepository.findById(id)
                .map(entity -> new TipoCombustible(entity.getId(), entity.getNombre()));
    }

    @Override
    public List<TipoCombustible> findAll() {
        return tipoCombustibleJpaRepository.findAll().stream()
                .map(entity -> new TipoCombustible(entity.getId(), entity.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        tipoCombustibleJpaRepository.deleteById(id);
    }
}