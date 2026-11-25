CREATE DATABASE IF NOT EXISTS sistema_usuarios CHARACTER SET utf8mb4;
USE sistema_usuarios;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    correo VARCHAR(80) NOT NULL,
    clave VARCHAR(64) NOT NULL
);

select * from usuarios;