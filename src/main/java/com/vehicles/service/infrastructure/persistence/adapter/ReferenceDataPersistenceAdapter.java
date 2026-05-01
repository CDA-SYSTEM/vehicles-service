package com.vehicles.service.infrastructure.persistence.adapter;

import com.vehicles.service.application.port.out.ReferenceDataPersistencePort;
import com.vehicles.service.domain.model.reference.ReferenceData;
import com.vehicles.service.domain.model.reference.ReferenceType;
import com.vehicles.service.infrastructure.exception.NotFoundException;
import com.vehicles.service.infrastructure.persistence.entity.ClaseEntity;
import com.vehicles.service.infrastructure.persistence.entity.ColorEntity;
import com.vehicles.service.infrastructure.persistence.entity.LineaEntity;
import com.vehicles.service.infrastructure.persistence.entity.MarcaEntity;
import com.vehicles.service.infrastructure.persistence.entity.ReferenceDataEntity;
import com.vehicles.service.infrastructure.persistence.entity.TipoCombustibleEntity;
import com.vehicles.service.infrastructure.persistence.entity.TipoServicioEntity;
import com.vehicles.service.infrastructure.persistence.entity.TipoVehiculoEntity;
import com.vehicles.service.infrastructure.persistence.repository.ClaseJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.ColorJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.LineaJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.MarcaJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.TipoCombustibleJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.TipoServicioJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.TipoVehiculoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ReferenceDataPersistenceAdapter implements ReferenceDataPersistencePort {

    private final MarcaJpaRepository marcaJpaRepository;
    private final ClaseJpaRepository claseJpaRepository;
    private final LineaJpaRepository lineaJpaRepository;
    private final ColorJpaRepository colorJpaRepository;
    private final TipoVehiculoJpaRepository tipoVehiculoJpaRepository;
    private final TipoCombustibleJpaRepository tipoCombustibleJpaRepository;
    private final TipoServicioJpaRepository tipoServicioJpaRepository;

    @Override
    @Transactional
    public ReferenceData save(ReferenceType type, ReferenceData data) {
        return switch (type) {
            case MARCA -> saveVia(marcaJpaRepository, data, MarcaEntity::new);
            case CLASE -> saveVia(claseJpaRepository, data, ClaseEntity::new);
            case LINEA -> saveVia(lineaJpaRepository, data, LineaEntity::new);
            case COLOR -> saveVia(colorJpaRepository, data, ColorEntity::new);
            case TIPO_VEHICULO -> saveVia(tipoVehiculoJpaRepository, data, TipoVehiculoEntity::new);
            case TIPO_COMBUSTIBLE -> saveVia(tipoCombustibleJpaRepository, data, TipoCombustibleEntity::new);
            case TIPO_SERVICIO -> saveVia(tipoServicioJpaRepository, data, TipoServicioEntity::new);
        };
    }

    @Override
    public Optional<ReferenceData> findById(ReferenceType type, Long id) {
        return switch (type) {
            case MARCA -> findByIdVia(marcaJpaRepository, id);
            case CLASE -> findByIdVia(claseJpaRepository, id);
            case LINEA -> findByIdVia(lineaJpaRepository, id);
            case COLOR -> findByIdVia(colorJpaRepository, id);
            case TIPO_VEHICULO -> findByIdVia(tipoVehiculoJpaRepository, id);
            case TIPO_COMBUSTIBLE -> findByIdVia(tipoCombustibleJpaRepository, id);
            case TIPO_SERVICIO -> findByIdVia(tipoServicioJpaRepository, id);
        };
    }

    @Override
    public List<ReferenceData> findAll(ReferenceType type) {
        return switch (type) {
            case MARCA -> findAllVia(marcaJpaRepository);
            case CLASE -> findAllVia(claseJpaRepository);
            case LINEA -> findAllVia(lineaJpaRepository);
            case COLOR -> findAllVia(colorJpaRepository);
            case TIPO_VEHICULO -> findAllVia(tipoVehiculoJpaRepository);
            case TIPO_COMBUSTIBLE -> findAllVia(tipoCombustibleJpaRepository);
            case TIPO_SERVICIO -> findAllVia(tipoServicioJpaRepository);
        };
    }

    @Override
    @Transactional
    public void deleteById(ReferenceType type, Long id) {
        switch (type) {
            case MARCA -> marcaJpaRepository.deleteById(id);
            case CLASE -> claseJpaRepository.deleteById(id);
            case LINEA -> lineaJpaRepository.deleteById(id);
            case COLOR -> colorJpaRepository.deleteById(id);
            case TIPO_VEHICULO -> tipoVehiculoJpaRepository.deleteById(id);
            case TIPO_COMBUSTIBLE -> tipoCombustibleJpaRepository.deleteById(id);
            case TIPO_SERVICIO -> tipoServicioJpaRepository.deleteById(id);
        }
    }

    private <E> ReferenceData saveVia(org.springframework.data.jpa.repository.JpaRepository<E, Long> repository,
                                      ReferenceData data,
                                      Supplier<E> constructor) {
        E entity = constructor.get();
        if (data.id() != null) {
            setId(entity, data.id());
        }
        setNombre(entity, data.nombre());
        return toDomain(repository.save(entity));
    }

    private <E> Optional<ReferenceData> findByIdVia(org.springframework.data.jpa.repository.JpaRepository<E, Long> repository,
                                                    Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    private <E> List<ReferenceData> findAllVia(org.springframework.data.jpa.repository.JpaRepository<E, Long> repository) {
        return repository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private ReferenceData toDomain(Object entity) {
        if (entity instanceof MarcaEntity marca) {
            return new ReferenceData(marca.getId(), marca.getNombre());
        }
        if (entity instanceof ClaseEntity clase) {
            return new ReferenceData(clase.getId(), clase.getNombre());
        }
        if (entity instanceof LineaEntity linea) {
            return new ReferenceData(linea.getId(), linea.getNombre());
        }
        if (entity instanceof ColorEntity color) {
            return new ReferenceData(color.getId(), color.getNombre());
        }
        if (entity instanceof TipoVehiculoEntity tipoVehiculo) {
            return new ReferenceData(tipoVehiculo.getId(), tipoVehiculo.getNombre());
        }
        if (entity instanceof TipoCombustibleEntity tipoCombustible) {
            return new ReferenceData(tipoCombustible.getId(), tipoCombustible.getNombre());
        }
        if (entity instanceof TipoServicioEntity tipoServicio) {
            return new ReferenceData(tipoServicio.getId(), tipoServicio.getNombre());
        }
        throw new NotFoundException("Entidad de referencia no soportada: " + entity.getClass().getName());
    }

    private void setId(Object entity, Long id) {
        if (entity instanceof ReferenceDataEntity reference) {
            reference.setId(id);
        }
    }

    private void setNombre(Object entity, String nombre) {
        if (entity instanceof ReferenceDataEntity reference) {
            reference.setNombre(nombre);
        }
    }
}
