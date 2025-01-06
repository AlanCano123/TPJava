
# Proyecto de Gestión de Costos y Acreditaciones



## Descripción
Este proyecto implementa un sistema para gestionar

- Puntos de Venta: Lugares desde donde se realizan transacciones.
- Costos: Rutas con sus costos asociados entre puntos de venta.
- Acreditaciones: Registro de ingresos con información enriquecida sobre los puntos de venta.



## Características Principales

*Puntos de Venta*

- CRUD completo para gestionar puntos de venta.

*Costos*

- Registrar rutas y costos entre puntos de venta.

- Calcular el costo mínimo entre dos puntos.

- Consultar todas las rutas conectadas a un punto.

*Acreditaciones*

Registrar acreditaciones con:

-Importe.

-Identificador de punto de venta.

-Fecha de recepción (automática).

-Nombre del punto de venta (enriquecido desde el cache).


## Tecnologías Utilizadas

- *Java 21*: Lenguaje principal.

- *Spring Boot 3.x*: Framework para desarrollo del backend.

- *Hibernate/JPA*: Para la persistencia de datos.

- *H2*: Base de datos en memoria para pruebas.

- *JUnit 5*: Para pruebas unitarias.

- *Postman*: Para pruebas de endpoints.

## Consideraciones

- Se adjunta el archivo de Postman para probar los endpoints.
- Las rutas de los costos van en ambas direcciones, por ejemplo:
**Ida: 1 Vuelta: 2 Costo: 4** es lo mismo que **Ida:2 Vuelta 1 Costo 4**
- La fecha de las acreditaciones es la fecha actual.
- Para resolver el costo minimo se utilizó grafos. Para el recorrido de los grafos se uso el algoritmo de Dijkstra



## Documentacion

- [Tutorial de Dijkstra en Java](https://www.youtube.com/watch?v=O9KGbr2Cx9A)
- [Tutorial de Hasmap](https://www.youtube.com/watch?v=RplZeK1bO4k)
- [Colas con prioridad](https://www.youtube.com/watch?v=ngstCxG0ug8)
- [Tutorial base de datos H2 Java](https://www.youtube.com/watch?v=rUq1o2fTxUY)
- [Tutorial Testing Junit 5](https://www.youtube.com/watch?v=flpmSXVTqBI)







## Autor

- [@Alan Cano](https://github.com/AlanCano123)

