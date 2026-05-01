package com.vehicles.service.application.port.in;

import com.vehicles.service.domain.model.command.CreateVehicleCommand;
import com.vehicles.service.domain.model.command.UpdateVehicleCommand;
import com.vehicles.service.domain.model.response.VehicleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface VehicleUseCase {

    VehicleResponse createVehicle(CreateVehicleCommand command);

    Optional<VehicleResponse> findVehicleById(Long id);

    Page<VehicleResponse> findVehicles(String clienteId, Pageable pageable);

    Optional<VehicleResponse> updateVehicle(Long id, UpdateVehicleCommand command);

    void deleteVehicle(Long id);
}
