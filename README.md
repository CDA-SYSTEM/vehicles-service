# Vehicles Service

Proyecto Spring Boot para el servicio de vehículos usando arquitectura hexagonal.

## Arquitectura

```
UI (React) → API Gateway → vehicles-service (Spring Boot, puerto 9000)
                               ├── PostgreSQL (vehículos)
                               ├── RabbitMQ (eventos: vehiculo.registro.creado)
                               └── Cliente-Servicio (HTTP)
```

## Variables de entorno

El servicio se configura mediante variables de entorno (inyectadas por Docker o vía `.env` + `DotenvLoader`). Spring Boot detecta automáticamente las variables con prefijo `SPRING_`, y el `DotenvLoader` mapea las claves cortas (`DB_URL`, `API_KEY`) al nombre Spring correspondiente.

### Base de datos (PostgreSQL)

| Variable | Descripción | Local | Servidor |
|----------|-------------|-------|----------|
| `SPRING_DATASOURCE_URL` | URL JDBC de conexión | `jdbc:postgresql://localhost:5432/vehicles` | `jdbc:postgresql://100.94.204.56:5432/vehicles` |
| `SPRING_DATASOURCE_USERNAME` | Usuario de BD | `postgres` | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Contraseña de BD | `1234` | `1423` |

### RabbitMQ (Broker de eventos)

| Variable | Descripción | Local | Servidor |
|----------|-------------|-------|----------|
| `SPRING_RABBITMQ_HOST` | Host del broker | `localhost` | `100.94.204.56` |
| `SPRING_RABBITMQ_PORT` | Puerto AMQP | `5672` | `5672` |
| `SPRING_RABBITMQ_USERNAME` | Usuario | `guest` | `admin` |
| `SPRING_RABBITMQ_PASSWORD` | Contraseña | `guest` | `admin123` |
| `SPRING_RABBITMQ_VIRTUAL_HOST` | Virtual host | `/` | `/` |

### Exchange de eventos (Topic)

| Variable | Descripción | Valor |
|----------|-------------|-------|
| `APP_RABBITMQ_EVENT_EXCHANGE` | Exchange de eventos para tracker service | `cda.domain.events` |
| `APP_RABBITMQ_ROUTING_KEY_VEHICULO` | Routing key al crear vehículo | `vehiculo.registro.creado` |

### Cola RPC (form-service)

| Variable | Descripción | Valor |
|----------|-------------|-------|
| `APP_RABBITMQ_VEHICLE_QUEUE` | Cola RPC para form-service | `vehicle-service-queue` |
| `APP_RABBITMQ_RPC_TIMEOUT_MS` | Timeout RPC (ms) | `8000` |

### Seguridad

| Variable | Descripción | Local | Servidor |
|----------|-------------|-------|----------|
| `API_KEY` | API Key para header `x-api-key` | `7b00f798...` | `540dda2ab2...` |

> La base de datos debe existir en PostgreSQL antes de ejecutar la aplicación. Flyway crea las tablas automáticamente.

## Ejecución local

### 1. Configurar entorno

Copia el archivo de ejemplo y ajusta las credenciales:

```powershell
copy .env.example .env
# Edita .env con tus valores locales
```

### 2. Ejecutar con Maven

```powershell
.\mvnw spring-boot:run
```

O empaquetar y ejecutar el JAR:

```powershell
.\mvnw package
java -jar target/service-0.0.1-SNAPSHOT.jar
```

El servicio arranca en el puerto definido por `SERVER_PORT` (`9000` en servidor, `8080` en local).

## Postman Collection

La colección de Postman se encuentra en `postman/vehicles-service-crud-catalogs.json`.

### Configuración de variables en Postman

| Variable | Local | Servidor |
|----------|-------|----------|
| `baseUrl` | `http://localhost:8080` | `http://100.94.204.56:9000` |
| `apiKey` | `7b00f798429fc8ef840ea4720277117f` | `540dda2ab2ef770dff4fbc5ee869f898875040ca7b3eefc2bd7c747a7bf310f2` |

Importa la colección en Postman, ajusta `baseUrl` y `apiKey` en **Collection → Variables**, y las peticiones incluirán automáticamente el header `x-api-key`.
