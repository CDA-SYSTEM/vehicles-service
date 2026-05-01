package com.vehicles.service.infrastructure.persistence.adapter;

import com.vehicles.service.application.port.out.TipoServicioPersistencePort;
import com.vehicles.service.domain.model.TipoServicio;
import com.vehicles.service.infrastructure.persistence.entity.TipoServicioEntity;
import com.vehicles.service.infrastructure.persistence.repository.TipoServicioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TipoServicioPersistenceAdapter implements TipoServicioPersistencePort {

    private final TipoServicioJpaRepository tipoServicioJpaRepository;

    @Override
    @Transactional
    public TipoServicio save(TipoServicio tipoServicio) {
        TipoServicioEntity entity = new TipoServicioEntity(tipoServicio.getId(), tipoServicio.getNombre());
        TipoServicioEntity saved = tipoServicioJpaRepository.save(entity);
        return new TipoServicio(saved.getId(), saved.getNombre());
    }

    @Override
    public Optional<TipoServicio> findById(Long id) {
        return tipoServicioJpaRepository.findById(id)
                .map(entity -> new TipoServicio(entity.getId(), entity.getNombre()));
    }

    @Override
    public List<TipoServicio> findAll() {
        return tipoServicioJpaRepository.findAll().stream()
                .map(entity -> new TipoServicio(entity.getId(), entity.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        tipoServicioJpaRepository.deleteById(id);
    }
}