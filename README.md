# Vehicles Service

Proyecto Spring Boot para el servicio de vehículos usando arquitectura hexagonal.

## Variables de entorno requeridas

El proyecto utiliza `application.properties` para leer la configuración de la conexión a PostgreSQL mediante variables de entorno.

Las variables que deben definirse en el entorno son:

- `DB_URL` - URL de conexión a PostgreSQL
- `DB_USER` - usuario de base de datos
- `DB_PASSWORD` - contraseña de base de datos

Ejemplo de valores:

```powershell
$env:DB_URL = 'jdbc:postgresql://localhost:5432/vehicles'
$env:DB_USER = 'postgres'
$env:DB_PASSWORD = '1234'
```

> Importante: la base de datos debe existir en PostgreSQL antes de ejecutar la aplicación. Flyway crea las tablas dentro de la base de datos.

## Ejecución local

Desde la raíz del proyecto:

```powershell
.\mvnw spring-boot:run
```

O empaquetar y ejecutar el JAR:

```powershell
.\mvnw package
java -jar target/service-0.0.1-SNAPSHOT.jar
```

## Ejemplo de archivo de entorno

Puedes usar el archivo `.env.example` como referencia para compartir con el equipo.
