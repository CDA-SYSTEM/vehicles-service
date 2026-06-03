package com.vehicles.service.application.service;

import com.vehicles.service.application.port.out.StatsPersistencePort;
import com.vehicles.service.infrastructure.web.dto.StatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final StatsPersistencePort statsPersistencePort;

    public StatsResponse getStats() {
        CompletableFuture<Long> totalFuture = CompletableFuture.supplyAsync(statsPersistencePort::countTotal);
        CompletableFuture<List<Map<String, Object>>> brandFuture = CompletableFuture.supplyAsync(statsPersistencePort::countByBrand);
        CompletableFuture<List<Map<String, Object>>> typeFuture = CompletableFuture.supplyAsync(statsPersistencePort::countByType);
        CompletableFuture<List<Map<String, Object>>> fuelFuture = CompletableFuture.supplyAsync(statsPersistencePort::countByFuelType);
        CompletableFuture<List<Map<String, Object>>> serviceFuture = CompletableFuture.supplyAsync(statsPersistencePort::countByServiceType);

        return new StatsResponse(
                totalFuture.join(),
                brandFuture.join(),
                typeFuture.join(),
                fuelFuture.join(),
                serviceFuture.join()
        );
    }
}
