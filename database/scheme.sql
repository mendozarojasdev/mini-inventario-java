CREATE DATABASE inventario;
USE inventario;

CREATE TABLE productos (
	id INT PRIMARY KEY AUTO_INCREMENT,
	codigo_barras VARCHAR(10),
	nombre VARCHAR(100),
	precio DECIMAL(10,2),
	existencias INT
);