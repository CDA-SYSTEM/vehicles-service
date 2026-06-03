package com.vehicles.service.infrastructure.persistence.adapter;

import com.vehicles.service.application.port.out.StatsPersistencePort;
import com.vehicles.service.infrastructure.persistence.repository.VehicleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class StatsPersistenceAdapter implements StatsPersistencePort {

    private final VehicleJpaRepository vehicleJpaRepository;

    @Override
    public long countTotal() {
        return vehicleJpaRepository.countTotal();
    }

    @Override
    public List<Map<String, Object>> countByBrand() {
        return vehicleJpaRepository.countByBrand().stream()
                .map(row -> Map.of("label", row[0], "count", row[1]))
                .toList();
    }

    @Override
    public List<Map<String, Object>> countByType() {
        return vehicleJpaRepository.countByType().stream()
                .map(row -> Map.of("label", row[0], "count", row[1]))
                .toList();
    }

    @Override
    public List<Map<String, Object>> countByFuelType() {
        return vehicleJpaRepository.countByFuelType().stream()
                .map(row -> Map.of("label", row[0], "count", row[1]))
                .toList();
    }

    @Override
    public List<Map<String, Object>> countByServiceType() {
        return vehicleJpaRepository.countByServiceType().stream()
                .map(row -> Map.of("label", row[0], "count", row[1]))
                .toList();
    }
}
