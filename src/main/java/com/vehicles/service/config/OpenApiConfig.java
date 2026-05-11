package com.vehicles.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI vehiclesServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Vehicle Service API")
                        .version("1.0")
                        .description("""
                                Gestión de vehículos y catálogos de referencia.

                                Rutas principales:
                                - Catálogos directos: `/marca`, `/clase`, `/linea`, `/color`, `/tipo-vehiculo`, `/tipo-combustible`, `/tipo-servicio`
                                - Vehículos: `/vehiculo` (incluye listado paginado y filtro por `clienteId`)
                                - Salud: `GET /api/v1/health`
                                - Catálogo unificado: `/api/v1/catalogs/{type}` con type = `marcas`, `clases`, `lineas`, `colores`, `tipos-vehiculo`, `tipos-combustible`, `tipos-servicio`
                                """)
                        .contact(new Contact().name("CDA System")))
                .servers(List.of(
                        new Server()
                                .url("/")
                                .description("Mismo host y puerto donde se ejecuta la aplicación")
                ));
    }
}
