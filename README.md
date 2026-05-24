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

El servicio se configura mediante variables de entorno (inyectadas por Docker o vía `.env` + `DotenvLoader`).

### Base de datos (PostgreSQL)

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `DB_URL` | URL JDBC de conexión | `jdbc:postgresql://host:5432/vehicles` |
| `DB_USER` | Usuario de BD | `postgres` |
| `DB_PASSWORD` | Contraseña de BD | `1423` |

### RabbitMQ (Broker de eventos)

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `RABBITMQ_HOST` | Host del broker | `100.94.204.56` |
| `RABBITMQ_PORT` | Puerto AMQP | `5672` |
| `RABBITMQ_QUEUE_USER` | Usuario RabbitMQ | `admin` |
| `RABBITMQ_PASSWORD` | Contraseña RabbitMQ | `admin` |
| `RABBITMQ_VHOST` | Virtual host | `/` |
| `RABBITMQ_VEHICLE_QUEUE` | Cola RPC vehicle-service | `vehicle-service-queue` |
| `RABBITMQ_RPC_TIMEOUT_MS` | Timeout RPC (ms) | `8000` |

### Seguridad

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| `API_KEY` | API Key para x-api-key header | `540dda2ab2ef...` |

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

El servicio arranca en el puerto definido por `SERVER_PORT` (por defecto `9000` en producción, `8080` en local).

## Postman Collection

La colección de Postman se encuentra en `postman/vehicles-service-crud-catalogs.json`.

### Configuración de variables en Postman

| Variable | Valor local | Valor servidor |
|----------|-------------|----------------|
| `baseUrl` | `http://localhost:8080` | `http://100.94.204.56:9000` |
| `apiKey` | `7b00f798429fc8ef840ea4720277117f` | `540dda2ab2ef770dff4fbc5ee869f898875040ca7b3eefc2bd7c747a7bf310f2` |

Importa la colección en Postman, ajusta `baseUrl` y `apiKey` en **Collection → Variables**, y las peticiones incluirán automáticamente el header `x-api-key`.
