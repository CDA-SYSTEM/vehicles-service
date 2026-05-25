package com.vehicles.service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "ApiKeyAuth";

    @Bean
    public OpenAPI vehiclesServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vehicle Service API")
                        .version("1.0")
                        .description("""
                                Gestión de vehículos y catálogos de referencia.

                                ## Autenticación
                                Todas las rutas (excepto `/api/v1/health`) requieren el header `x-api-key`.
                                
                                ## Rutas principales
                                - Catálogos directos: `/marca`, `/clase`, `/linea`, `/color`, `/tipo-vehiculo`, `/tipo-combustible`, `/tipo-servicio`
                                - Vehículos: `/vehiculo` (incluye listado paginado y filtro por `clienteId`)
                                - Salud: `GET /api/v1/health`
                                - Catálogo unificado: `/api/v1/catalogs/{type}` con type = `marcas`, `clases`, `lineas`, `colores`, `tipos-vehiculo`, `tipos-combustible`, `tipos-servicio`

                                ## Integración asíncrona (RabbitMQ)
                                Al crear un vehículo (`POST /vehiculo`), el servicio publica un evento en RabbitMQ:
                                - **Exchange**: `cda.domain.events` (Topic)
                                - **Routing key**: `vehiculo.registro.creado`
                                - **Payload** (envelope):
                                  ```json
                                  {
                                    "eventId": "uuid",
                                    "timestamp": "2026-05-24T20:00:00Z",
                                    "eventType": "VehiculoRegistrado",
                                    "data": {
                                      "placa": "ABC-123",
                                      "marca": "Mazda",
                                      "modelo": 2024,
                                      "tipo": "Carro",
                                      "propietarioId": "CLI-001"
                                    }
                                  }
                                  ```
                                El tracker service (Flask) consume este evento para crear el nodo `:Vehiculo` y la relación `(Cliente)-[:POSEE]->(Vehiculo)` en Neo4j.
                                """)
                        .contact(new Contact().name("CDA System")))
                .servers(List.of(
                        new Server()
                                .url("/")
                                .description("Mismo host y puerto donde se ejecuta la aplicación")
                ))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, new SecurityScheme()
                                .name("x-api-key")
                                .type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .description("API Key para autenticación. Configurada mediante la variable API_KEY en .env")))
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_SCHEME_NAME));
    }
}
