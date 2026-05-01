package com.vehicles.service.infrastructure.persistence.adapter;

import com.vehicles.service.application.port.out.TipoVehiculoPersistencePort;
import com.vehicles.service.domain.model.TipoVehiculo;
import com.vehicles.service.infrastructure.persistence.entity.TipoVehiculoEntity;
import com.vehicles.service.infrastructure.persistence.repository.TipoVehiculoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TipoVehiculoPersistenceAdapter implements TipoVehiculoPersistencePort {

    private final TipoVehiculoJpaRepository tipoVehiculoJpaRepository;

    @Override
    @Transactional
    public TipoVehiculo save(TipoVehiculo tipoVehiculo) {
        TipoVehiculoEntity entity = new TipoVehiculoEntity(tipoVehiculo.getId(), tipoVehiculo.getNombre());
        TipoVehiculoEntity saved = tipoVehiculoJpaRepository.save(entity);
        return new TipoVehiculo(saved.getId(), saved.getNombre());
    }

    @Override
    public Optional<TipoVehiculo> findById(Long id) {
        return tipoVehiculoJpaRepository.findById(id)
                .map(entity -> new TipoVehiculo(entity.getId(), entity.getNombre()));
    }

    @Override
    public List<TipoVehiculo> findAll() {
        return tipoVehiculoJpaRepository.findAll().stream()
                .map(entity -> new TipoVehiculo(entity.getId(), entity.getNombre()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        tipoVehiculoJpaRepository.deleteById(id);
    }
}