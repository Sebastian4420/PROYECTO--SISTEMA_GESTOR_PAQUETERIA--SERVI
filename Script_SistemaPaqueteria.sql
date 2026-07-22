CREATE DATABASE sistema_paqueteria;
USE sistema_paqueteria;

CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    descripcion VARCHAR(150)
);

INSERT INTO roles (nombre, descripcion) VALUES
('Administrador', 'Acceso completo al sistema'),
('Operador', 'Gestiona clientes y paquetes'),
('Repartidor', 'Actualiza el estado de las entregas'),
('Cliente', 'Consulta el estado de sus paquetes');


CREATE TABLE tipos_envio (
    id_tipo_envio INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    descripcion VARCHAR(150)
);

INSERT INTO tipos_envio (nombre, descripcion) VALUES
('Normal', 'Entrega estándar'),
('Express', 'Entrega prioritaria'),
('Internacional', 'Envíos fuera del país');

CREATE TABLE estados_paquete (
    id_estado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE,
    descripcion VARCHAR(150)
);

INSERT INTO estados_paquete (nombre, descripcion) VALUES
('Registrado', 'El paquete fue registrado en el sistema'),
('En bodega', 'El paquete se encuentra en la bodega'),
('En tránsito', 'El paquete está siendo transportado'),
('En reparto', 'El repartidor está realizando la entrega'),
('Entregado', 'El paquete fue entregado al destinatario'),
('Cancelado', 'El envío fue cancelado');


CREATE TABLE tarifas (
    id_tarifa INT AUTO_INCREMENT PRIMARY KEY,
    id_tipo_envio INT NOT NULL,
    precio_base DECIMAL(10,2) NOT NULL,
    costo_por_kg_extra DECIMAL(10,2) NOT NULL,
    recargo DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (id_tipo_envio) REFERENCES tipos_envio(id_tipo_envio)
);

INSERT INTO tarifas (id_tipo_envio, precio_base, costo_por_kg_extra, recargo)
VALUES
(1,3.00,0.80,0.00),
(2,3.00,0.80,5.00),
(3,3.00,0.80,15.00);

SELECT*from estados_paquete;


CREATE TABLE clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(10) NOT NULL UNIQUE,
    nombres VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    direccion VARCHAR(150) NOT NULL,
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP
);



CREATE TABLE repartidores (
    id_repartidor INT AUTO_INCREMENT PRIMARY KEY,
    cedula VARCHAR(10) NOT NULL UNIQUE,
    nombres VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    vehiculo VARCHAR(50) NOT NULL,
    placa VARCHAR(10) NOT NULL UNIQUE,
    disponible BOOLEAN DEFAULT TRUE
);



CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    id_rol INT NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
);



CREATE TABLE paquetes (
    id_paquete INT AUTO_INCREMENT PRIMARY KEY,
    codigo_seguimiento VARCHAR(20) NOT NULL UNIQUE,

    id_cliente INT NOT NULL,
    id_repartidor INT,
    
    nombre_destinatario VARCHAR(100) NOT NULL,
	telefono_destinatario VARCHAR(15) NOT NULL,

    id_tipo_envio INT NOT NULL,
    id_estado INT NOT NULL,

    origen VARCHAR(150) NOT NULL,
    destino VARCHAR(150) NOT NULL,

    peso DECIMAL(6,2) NOT NULL,
    precio DECIMAL(8,2) NOT NULL,

    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (id_cliente)
        REFERENCES clientes(id_cliente),

    FOREIGN KEY (id_repartidor)
        REFERENCES repartidores(id_repartidor),

    FOREIGN KEY (id_tipo_envio)
        REFERENCES tipos_envio(id_tipo_envio),

    FOREIGN KEY (id_estado)
        REFERENCES estados_paquete(id_estado)
);




CREATE TABLE historial_estados (
    id_historial INT AUTO_INCREMENT PRIMARY KEY,

    id_paquete INT NOT NULL,

    id_estado INT NOT NULL,

    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,

    observacion VARCHAR(200),

    FOREIGN KEY (id_paquete)
        REFERENCES paquetes(id_paquete),

    FOREIGN KEY (id_estado)
        REFERENCES estados_paquete(id_estado)
);

use sistema_paqueteria;
SELECT *
FROM historial_estados
WHERE id_paquete = (
    SELECT id_paquete
    FROM paquetes
    WHERE codigo_seguimiento = 'PK-4F3C6134'
);

ALTER TABLE clientes
ADD CONSTRAINT uk_clientes_cedula UNIQUE (cedula),
ADD CONSTRAINT uk_clientes_telefono UNIQUE (telefono);

