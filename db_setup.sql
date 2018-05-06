CREATE USER 'inventory_user'@'localhost' IDENTIFIED BY 'inventory_pass';
GRANT ALL PRIVILEGES ON * . * TO 'inventory_user'@'localhost';

CREATE DATABASE INVENTORY_DB;
USE INVENTORY_DB;

CREATE TABLE product
(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	description VARCHAR(1024) NOT NULL,
	quantity INT NOT NULL,
	PRIMARY KEY (id)
);

INSERT INTO product (name, description, quantity) VALUES ('OCZ Octane 600', '600W PSU for high-power computer systems.', 100);
INSERT INTO product (name, description, quantity) VALUES ('ASUS AT-P100', 'Standard ATX motherboard for Intel LGA 1155 CPUs. Includes two PCIe-x16 slots for graphics cards and supports SLI.', 50);
INSERT INTO product (name, description, quantity) VALUES ('Microsoft Wave Keyboard', 'An ergonomic, multimedia keyboard', 89);
INSERT INTO product (name, description, quantity) VALUES ('Gigabyte G2', '1200 DPI gaming mouse with 6 gaming buttons.', 0);
INSERT INTO product (name, description, quantity) VALUES ('LG HD2250', '22" Backlight-LED monitor with touchscreen panel and VESA mounts.', 4);
INSERT INTO product (name, description, quantity) VALUES ('Intel G30', '3.0 GHZ Dual-core LGA 1155 CPU with 2MB L3 cache', 45);
INSERT INTO product (name, description, quantity) VALUES ('Nvidia Geforce 690x', '150W GPU with 2048 shader units and 3GB video memory', 19);

