DROP TABLE IF EXISTS punto;
DROP TABLE IF EXISTS costos;
DROP TABLE IF EXISTS acreditacion;
CREATE TABLE punto (
                               id INT AUTO_INCREMENT  PRIMARY KEY,
                               nombre VARCHAR(250) NOT NULL
);
CREATE TABLE costos (id INT AUTO_INCREMENT PRIMARY KEY,
                     ida INT NOT NULL,
                     vuelta INT NOT NULL,
                     costo INT NOT NULL,
                     CONSTRAINT fk_ida FOREIGN KEY (ida) REFERENCES punto(id),
                     CONSTRAINT fk_vuelta FOREIGN KEY (vuelta) REFERENCES punto(id)
);
CREATE TABLE acreditacion (
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              importe DOUBLE,
                              punto_venta_id INT,
                              fecha_recepcion DATETIME,
                              nombre_punto_venta VARCHAR(255),
                              CONSTRAINT fk_acreditacion_punto FOREIGN KEY (punto_venta_id) REFERENCES punto(id)
);