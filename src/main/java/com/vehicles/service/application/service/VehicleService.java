package com.vehicles.service.application.service;

import com.vehicles.service.application.port.in.VehicleUseCase;
import com.vehicles.service.application.port.out.VehiclePersistencePort;
import com.vehicles.service.domain.model.Vehicle;
import com.vehicles.service.domain.model.command.CreateVehicleCommand;
import com.vehicles.service.domain.model.command.UpdateVehicleCommand;
import com.vehicles.service.domain.model.reference.ReferenceData;
import com.vehicles.service.domain.model.response.VehicleResponse;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class VehicleService implements VehicleUseCase {

    private final VehiclePersistencePort persistencePort;

    @Override
    public VehicleResponse createVehicle(CreateVehicleCommand command) {
        Vehicle vehicle = mapToDomain(null, command);
        Vehicle saved = persistencePort.save(vehicle);
        return toResponse(saved);
    }

    @Override
    public Optional<VehicleResponse> findVehicleById(Long id) {
        return persistencePort.findById(id).map(this::toResponse);
    }

    @Override
    public Page<VehicleResponse> findVehicles(String clienteId, Pageable pageable) {
        Page<Vehicle> vehicles = clienteId == null || clienteId.isBlank()
                ? persistencePort.findAll(pageable)
                : persistencePort.findByClienteId(clienteId, pageable);
        return vehicles.map(this::toResponse);
    }

    @Override
    public Optional<VehicleResponse> updateVehicle(Long id, UpdateVehicleCommand command) {
        return persistencePort.findById(id)
                .map(existing -> persistencePort.save(mapToDomain(id, command)))
                .map(this::toResponse);
    }

    @Override
    public void deleteVehicle(Long id) {
        persistencePort.deleteById(id);
    }

    private Vehicle mapToDomain(Long id, CreateVehicleCommand command) {
        return new Vehicle(
                id,
                command.clienteId(),
                command.marcaId(),
                null,
                command.claseId(),
                null,
                command.lineaId(),
                null,
                command.colorId(),
                null,
                command.tipoVehiculoId(),
                null,
                command.tipoCombustibleId(),
                null,
                command.tipoServicioId(),
                null,
                command.modelo(),
                command.placa(),
                command.certificadoNo()
        );
    }

    private Vehicle mapToDomain(Long id, UpdateVehicleCommand command) {
        return new Vehicle(
                id,
                command.clienteId(),
                command.marcaId(),
                null,
                command.claseId(),
                null,
                command.lineaId(),
                null,
                command.colorId(),
                null,
                command.tipoVehiculoId(),
                null,
                command.tipoCombustibleId(),
                null,
                command.tipoServicioId(),
                null,
                command.modelo(),
                command.placa(),
                command.certificadoNo()
        );
    }

    private VehicleResponse toResponse(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.id(),
                vehicle.clienteId(),
                createReference(vehicle.marcaId(), vehicle.marcaNombre()),
                createReference(vehicle.claseId(), vehicle.claseNombre()),
                createReference(vehicle.lineaId(), vehicle.lineaNombre()),
                createReference(vehicle.colorId(), vehicle.colorNombre()),
                createReference(vehicle.tipoVehiculoId(), vehicle.tipoVehiculoNombre()),
                createReference(vehicle.tipoCombustibleId(), vehicle.tipoCombustibleNombre()),
                createReference(vehicle.tipoServicioId(), vehicle.tipoServicioNombre()),
                vehicle.modelo(),
                vehicle.placa(),
                vehicle.certificadoNo()
        );
    }

    private ReferenceData createReference(Long id, String nombre) {
        return id == null ? null : new ReferenceData(id, nombre);
    }
}
