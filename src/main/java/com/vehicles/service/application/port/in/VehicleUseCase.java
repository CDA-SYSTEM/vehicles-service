package com.vehicles.service.application.port.in;

import com.vehicles.service.domain.model.command.CreateVehicleCommand;
import com.vehicles.service.domain.model.response.VehicleResponse;

import java.util.Optional;

public interface VehicleUseCase {

    VehicleResponse createVehicle(CreateVehicleCommand command);

    Optional<VehicleResponse> findVehicleById(Long id);
}
