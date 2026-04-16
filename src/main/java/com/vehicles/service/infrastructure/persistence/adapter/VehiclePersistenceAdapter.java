package com.vehicles.service.infrastructure.persistence.adapter;

import com.vehicles.service.application.port.out.VehiclePersistencePort;
import com.vehicles.service.domain.model.Vehicle;
import com.vehicles.service.infrastructure.exception.NotFoundException;
import com.vehicles.service.infrastructure.persistence.entity.ClaseEntity;
import com.vehicles.service.infrastructure.persistence.entity.ColorEntity;
import com.vehicles.service.infrastructure.persistence.entity.LineaEntity;
import com.vehicles.service.infrastructure.persistence.entity.MarcaEntity;
import com.vehicles.service.infrastructure.persistence.entity.TipoCombustibleEntity;
import com.vehicles.service.infrastructure.persistence.entity.TipoServicioEntity;
import com.vehicles.service.infrastructure.persistence.entity.TipoVehiculoEntity;
import com.vehicles.service.infrastructure.persistence.entity.VehicleEntity;
import com.vehicles.service.infrastructure.persistence.repository.ClaseJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.ColorJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.LineaJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.MarcaJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.TipoCombustibleJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.TipoServicioJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.TipoVehiculoJpaRepository;
import com.vehicles.service.infrastructure.persistence.repository.VehicleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class VehiclePersistenceAdapter implements VehiclePersistencePort {

    private final VehicleJpaRepository vehicleJpaRepository;
    private final MarcaJpaRepository marcaJpaRepository;
    private final ClaseJpaRepository claseJpaRepository;
    private final LineaJpaRepository lineaJpaRepository;
    private final ColorJpaRepository colorJpaRepository;
    private final TipoVehiculoJpaRepository tipoVehiculoJpaRepository;
    private final TipoCombustibleJpaRepository tipoCombustibleJpaRepository;
    private final TipoServicioJpaRepository tipoServicioJpaRepository;

    @Override
    public Vehicle save(Vehicle vehicle) {
        VehicleEntity entity = toEntity(vehicle);
        VehicleEntity saved = vehicleJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Vehicle> findById(Long id) {
        return vehicleJpaRepository.findById(id).map(this::toDomain);
    }

    private VehicleEntity toEntity(Vehicle vehicle) {
        VehicleEntity entity = new VehicleEntity();
        if (vehicle.id() != null) {
            entity.setId(vehicle.id());
        }
        entity.setClienteId(vehicle.clienteId());
        entity.setMarca(loadReference(marcaJpaRepository::findById, "Marca", vehicle.marcaId()));
        entity.setClase(loadReference(claseJpaRepository::findById, "Clase", vehicle.claseId()));
        entity.setLinea(loadReference(lineaJpaRepository::findById, "Linea", vehicle.lineaId()));
        entity.setColor(loadReference(colorJpaRepository::findById, "Color", vehicle.colorId()));
        entity.setTipoVehiculo(loadReference(tipoVehiculoJpaRepository::findById, "Tipo de Vehículo", vehicle.tipoVehiculoId()));
        entity.setTipoCombustible(loadReference(tipoCombustibleJpaRepository::findById, "Tipo de Combustible", vehicle.tipoCombustibleId()));
        entity.setTipoServicio(loadReference(tipoServicioJpaRepository::findById, "Tipo de Servicio", vehicle.tipoServicioId()));
        entity.setModelo(vehicle.modelo());
        entity.setPlaca(vehicle.placa());
        entity.setCertificadoNo(vehicle.certificadoNo());
        return entity;
    }

    private Vehicle toDomain(VehicleEntity entity) {
        return new Vehicle(
                entity.getId(),
                entity.getClienteId(),
                entity.getMarca().getId(),
                entity.getClase().getId(),
                entity.getLinea().getId(),
                entity.getColor().getId(),
                entity.getTipoVehiculo().getId(),
                entity.getTipoCombustible().getId(),
                entity.getTipoServicio().getId(),
                entity.getModelo(),
                entity.getPlaca(),
                entity.getCertificadoNo()
        );
    }

    private <T> T loadReference(Function<Long, Optional<T>> finder, String type, Long id) {
        return finder.apply(id)
                .orElseThrow(() -> new NotFoundException(type + " no encontrada para id=" + id));
    }
}
