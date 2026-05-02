CREATE TABLE marca (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE clase (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE linea (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE color (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE tipo_vehiculo (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE tipo_combustible (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE tipo_servicio (
    id BIGSERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE vehiculo (
    id BIGSERIAL PRIMARY KEY,
    cliente_id VARCHAR(100) NOT NULL,
    marca_id BIGINT NOT NULL REFERENCES marca(id),
    clase_id BIGINT NOT NULL REFERENCES clase(id),
    linea_id BIGINT NOT NULL REFERENCES linea(id),
    color_id BIGINT NOT NULL REFERENCES color(id),
    tipo_vehiculo_id BIGINT NOT NULL REFERENCES tipo_vehiculo(id),
    tipo_combustible_id BIGINT NOT NULL REFERENCES tipo_combustible(id),
    tipo_servicio_id BIGINT NOT NULL REFERENCES tipo_servicio(id),
    modelo VARCHAR(100) NOT NULL,
    placa VARCHAR(50) NOT NULL UNIQUE,
    certificado_no VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);
