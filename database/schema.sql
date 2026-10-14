-- La tabla ya existe en Aiven; este script permite reproducirla en defaultdb.
CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    precio DECIMAL(10,2) NOT NULL
);
