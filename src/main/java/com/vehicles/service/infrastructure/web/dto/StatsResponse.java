package com.vehicles.service.infrastructure.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class StatsResponse {
    private long totalVehicles;
    private List<Map<String, Object>> byBrand;
    private List<Map<String, Object>> byType;
    private List<Map<String, Object>> byFuelType;
    private List<Map<String, Object>> byServiceType;
}
