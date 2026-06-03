package com.vehicles.service.application.port.out;

import java.util.List;
import java.util.Map;

public interface StatsPersistencePort {
    long countTotal();
    List<Map<String, Object>> countByBrand();
    List<Map<String, Object>> countByType();
    List<Map<String, Object>> countByFuelType();
    List<Map<String, Object>> countByServiceType();
}
