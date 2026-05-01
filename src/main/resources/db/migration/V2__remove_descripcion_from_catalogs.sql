-- V2: Remove descripcion column from all catalog tables
ALTER TABLE marca DROP COLUMN IF EXISTS descripcion;
ALTER TABLE clase DROP COLUMN IF EXISTS descripcion;
ALTER TABLE linea DROP COLUMN IF EXISTS descripcion;
ALTER TABLE color DROP COLUMN IF EXISTS descripcion;
ALTER TABLE tipo_vehiculo DROP COLUMN IF EXISTS descripcion;
ALTER TABLE tipo_combustible DROP COLUMN IF EXISTS descripcion;
ALTER TABLE tipo_servicio DROP COLUMN IF EXISTS descripcion;