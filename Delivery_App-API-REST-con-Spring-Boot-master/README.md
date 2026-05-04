DeliveryApp – API REST con Spring Boot

DeliveryApp es una API REST desarrollada con Spring Boot que simula el funcionamiento básico de una aplicación de domicilios (delivery).
Permite gestionar usuarios (clientes), restaurantes, productos, repartidores y pedidos, manejando relaciones reales entre las entidades.

El proyecto está pensado como práctica backend y para portafolio, aplicando buenas prácticas de desarrollo.

------------------------------------------------------------------------------------------------------------------------------------------

Arquitectura del Proyecto

El proyecto sigue una arquitectura Hexagonal

-------------------------------------------------------------------------

Tecnologías Utilizadas:

Java 21

Spring Boot

Spring Web

Spring Data JPA

Hibernate

Json

MySQL / H2

Maven

--------------------------------------------------------------------------------

Entidades Principales
Usuario


## Roles de Usuario

El sistema maneja los siguientes roles:

- CLIENTE → Realiza pedidos
- REPARTIDOR → Entrega pedidos
- RESTAURANTE → Gestiona productos (futuro)


Representa a los clientes del sistema.

Restaurante

Contiene información básica y una lista de productos asociados.

Producto

Pertenece a un restaurante y tiene estado de disponibilidad.

Repartidor

Usuario encargado de entregar los pedidos.

Pedido

Entidad principal que relaciona:

Cliente

Repartidor

Restaurante

Incluye:

Fecha

Total
------------------------------------------------------------

Estados (Enums)
EstadoPedido
PENDIENTE,
EN_PREPARACION,
EN_CAMINO,
ENTREGADO,
CANCELADO

DisponibilidadProducto
DISPONIBLE,
NO_DISPONIBLE

EstadoRestaurante
ABIERTO,
CERRADO

Endpoints Principales (URLs)
Usuarios
Usuarios
POST   /api/v2/usuarios
GET    /api/v2/usuarios
GET    /api/v2/usuarios/{id}

Restaurantes
POST   /api/v2/restaurantes
GET    /api/v2/restaurantes
GET    /api/v2/restaurantes/{id}
DELETE /api/v2/restaurantes/{id}

Productos
POST   /api/v2/productos
GET    /api/v2/productos
GET    /api/v2/productos/{id}
DELETE /api/v2/productos/{id}

Repartidores
POST   /api/v2/repartidores
GET    /api/v2/repartidores
GET    /api/v2/repartidores/{id}

Pedidos
POST   /api/v2/pedidos
GET    /api/v2/pedidos
GET    /api/v2/pedidos/{id}
DELETE /api/v2/pedidos/{id}
----------------------------------------------------------------------------------------
Ejemplo de JSON para crear un Pedido
{
"fecha": "2026-01-21",
"total": 25000,
"estadoPedido": "PENDIENTE",
"clienteId": 1,
"repartidorId": 2,
"restauranteId": 3
}
----------------------------------------------------------------------------------------------------------
Manejo de Relaciones y JSON

Para evitar ciclos infinitos al listar información, se utilizan:

@JsonManagedReference

@JsonBackReference

@JsonIgnore
-------------------------------------------------------------------------------

Cómo ejecutar el proyecto

Clonar el repositorio

git clone https://github.com/tu-usuario/DeliveryApp.git


Abrir el proyecto en IntelliJ IDEA o VS Code

Configurar la base de datos en application.properties

Ejecutar la clase principal:

DeliveryAppApplication.java

---------------------------------------------------------------------------------------

Probar los endpoints con Postman o Insomnia

--------------------------------------------------------------------------------------

Objetivo del Proyecto

Este proyecto fue desarrollado con el objetivo de:

Practicar desarrollo backend con Spring Boot

Implementar APIs REST

Manejar relaciones JPA

Aplicar buenas prácticas de programación

-------------------------------------------------------------------------------------
Estados (Enums)
EstadoPedido

PENDIENTE,
EN_PREPARACION,
EN_CAMINO,
ENTREGADO,
CANCELADO

------------------------------------------------------------------------------------
## Endpoints principales

- /api/v2/usuarios
- /api/v2/restaurantes
- /api/v2/productos
- /api/v2/repartidores
- /api/v2/pedidos


-----------------------------------------------------------------------------------


## Configuración de Base de Datos

El proyecto utiliza MySQL o H2.

Ejemplo en `application.properties`:

spring.datasource.url=jdbc:mysql://localhost:3306/delivery_app
spring.datasource.username=root
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


---------------------------------------------------------------------------------

## Mejoras futuras
- Autenticación con JWT
- Paginación


Proyecto desarrollado con fines educativos y de portafolio.
Autor Adalberto








