package com.vehicles.service.infrastructure.persistence.repository;

import com.vehicles.service.infrastructure.persistence.entity.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VehicleJpaRepository extends JpaRepository<VehicleEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"marca", "clase", "linea", "color", "tipoVehiculo", "tipoCombustible", "tipoServicio"})
    Optional<VehicleEntity> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"marca", "clase", "linea", "color", "tipoVehiculo", "tipoCombustible", "tipoServicio"})
    Page<VehicleEntity> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"marca", "clase", "linea", "color", "tipoVehiculo", "tipoCombustible", "tipoServicio"})
    Page<VehicleEntity> findByClienteId(String clienteId, Pageable pageable);

    Optional<VehicleEntity> findByPlaca(String placa);

    @Query("SELECT COUNT(v) FROM VehicleEntity v")
    long countTotal();

    @Query("SELECT m.nombre as label, COUNT(v) as count FROM VehicleEntity v JOIN v.marca m GROUP BY m.nombre")
    List<Object[]> countByBrand();

    @Query("SELECT tv.nombre as label, COUNT(v) as count FROM VehicleEntity v JOIN v.tipoVehiculo tv GROUP BY tv.nombre")
    List<Object[]> countByType();

    @Query("SELECT tc.nombre as label, COUNT(v) as count FROM VehicleEntity v JOIN v.tipoCombustible tc GROUP BY tc.nombre")
    List<Object[]> countByFuelType();

    @Query("SELECT ts.nombre as label, COUNT(v) as count FROM VehicleEntity v JOIN v.tipoServicio ts GROUP BY ts.nombre")
    List<Object[]> countByServiceType();
}
