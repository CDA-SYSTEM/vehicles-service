-- =============================================================
-- V2: Optimizaciones para producción
-- =============================================================
-- Mejora rendimiento de consultas del API Gateway y asegura
-- integridad de datos en catálogos.

-- -----------------------------------------------------------
-- 1. Índices para rendimiento del API Gateway
-- -----------------------------------------------------------

-- 1.1 Consultas por clienteId (GET /vehiculo?clienteId=... y GET /vehiculo/cliente/{clienteId})
CREATE INDEX IF NOT EXISTS idx_vehiculo_cliente_id ON vehiculo (cliente_id);

-- 1.2 Índices en claves foráneas para JOINs con catálogos
CREATE INDEX IF NOT EXISTS idx_vehiculo_marca_id ON vehiculo (marca_id);
CREATE INDEX IF NOT EXISTS idx_vehiculo_clase_id ON vehiculo (clase_id);
CREATE INDEX IF NOT EXISTS idx_vehiculo_linea_id ON vehiculo (linea_id);
CREATE INDEX IF NOT EXISTS idx_vehiculo_color_id ON vehiculo (color_id);
CREATE INDEX IF NOT EXISTS idx_vehiculo_tipo_vehiculo_id ON vehiculo (tipo_vehiculo_id);
CREATE INDEX IF NOT EXISTS idx_vehiculo_tipo_combustible_id ON vehiculo (tipo_combustible_id);
CREATE INDEX IF NOT EXISTS idx_vehiculo_tipo_servicio_id ON vehiculo (tipo_servicio_id);

-- 1.3 Índice para búsqueda por placa (además del UNIQUE ya existente)
CREATE INDEX IF NOT EXISTS idx_vehiculo_placa ON vehiculo (placa);

-- -----------------------------------------------------------
-- 2. Unique constraints en catálogos (evita duplicados)
-- -----------------------------------------------------------

ALTER TABLE marca ADD CONSTRAINT IF NOT EXISTS uq_marca_nombre UNIQUE (nombre);
ALTER TABLE clase ADD CONSTRAINT IF NOT EXISTS uq_clase_nombre UNIQUE (nombre);
ALTER TABLE linea ADD CONSTRAINT IF NOT EXISTS uq_linea_nombre UNIQUE (nombre);
ALTER TABLE color ADD CONSTRAINT IF NOT EXISTS uq_color_nombre UNIQUE (nombre);
ALTER TABLE tipo_vehiculo ADD CONSTRAINT IF NOT EXISTS uq_tipo_vehiculo_nombre UNIQUE (nombre);
ALTER TABLE tipo_combustible ADD CONSTRAINT IF NOT EXISTS uq_tipo_combustible_nombre UNIQUE (nombre);
ALTER TABLE tipo_servicio ADD CONSTRAINT IF NOT EXISTS uq_tipo_servicio_nombre UNIQUE (nombre);

-- -----------------------------------------------------------
-- 3. Audit columns en catálogos
-- -----------------------------------------------------------

ALTER TABLE marca ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();
ALTER TABLE marca ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();

ALTER TABLE clase ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();
ALTER TABLE clase ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();

ALTER TABLE linea ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();
ALTER TABLE linea ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();

ALTER TABLE color ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();
ALTER TABLE color ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();

ALTER TABLE tipo_vehiculo ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();
ALTER TABLE tipo_vehiculo ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();

ALTER TABLE tipo_combustible ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();
ALTER TABLE tipo_combustible ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();

ALTER TABLE tipo_servicio ADD COLUMN IF NOT EXISTS created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();
ALTER TABLE tipo_servicio ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW();
