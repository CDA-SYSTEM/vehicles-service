package com.vehicles.service.application.service;

import com.vehicles.service.application.port.in.VehicleUseCase;
import com.vehicles.service.application.port.out.VehiclePersistencePort;
import com.vehicles.service.domain.model.Vehicle;
import com.vehicles.service.domain.model.command.CreateVehicleCommand;
import com.vehicles.service.domain.model.response.VehicleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleService implements VehicleUseCase {

    private final VehiclePersistencePort persistencePort;

    @Override
    public VehicleResponse createVehicle(CreateVehicleCommand command) {
        Vehicle vehicle = new Vehicle(
                null,
                command.clienteId(),
                command.marcaId(),
                command.claseId(),
                command.lineaId(),
                command.colorId(),
                command.tipoVehiculoId(),
                command.tipoCombustibleId(),
                command.tipoServicioId(),
                command.modelo(),
                command.placa(),
                command.certificadoNo()
        );
        Vehicle saved = persistencePort.save(vehicle);
        return toResponse(saved);
    }

    @Override
    public Optional<VehicleResponse> findVehicleById(Long id) {
        return persistencePort.findById(id).map(this::toResponse);
    }

    private VehicleResponse toResponse(Vehicle vehicle) {
        return new VehicleResponse(
                vehicle.id(),
                vehicle.clienteId(),
                vehicle.marcaId(),
                vehicle.claseId(),
                vehicle.lineaId(),
                vehicle.colorId(),
                vehicle.tipoVehiculoId(),
                vehicle.tipoCombustibleId(),
                vehicle.tipoServicioId(),
                vehicle.modelo(),
                vehicle.placa(),
                vehicle.certificadoNo()
        );
    }
}
