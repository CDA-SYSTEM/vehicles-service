package com.vehicles.service.application.service;

import com.vehicles.service.application.port.out.TipoVehiculoPersistencePort;
import com.vehicles.service.domain.model.TipoVehiculo;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class TipoVehiculoService {

    private final TipoVehiculoPersistencePort persistencePort;

    public TipoVehiculo create(TipoVehiculo tipoVehiculo) {
        return persistencePort.save(tipoVehiculo);
    }

    public Optional<TipoVehiculo> findById(Long id) {
        return persistencePort.findById(id);
    }

    public List<TipoVehiculo> findAll() {
        return persistencePort.findAll();
    }

    public TipoVehiculo update(Long id, TipoVehiculo tipoVehiculo) {
        persistencePort.findById(id)
                .orElseThrow(() -> new IllegalStateException("No existe el tipo de vehículo con id=" + id));
        TipoVehiculo updated = new TipoVehiculo(id, tipoVehiculo.getNombre());
        return persistencePort.save(updated);
    }

    public void deleteById(Long id) {
        persistencePort.deleteById(id);
    }
}