DeliveryApp – API REST con Spring Boot

DeliveryApp es una API REST desarrollada con Spring Boot que simula el funcionamiento básico de una aplicación de domicilios (delivery).
Permite gestionar usuarios (clientes), restaurantes, productos, repartidores y pedidos, manejando relaciones reales entre las entidades.

El proyecto está pensado como práctica backend y para portafolio, aplicando buenas prácticas de desarrollo.

------------------------------------------------------------------------------------------------------------------------------------------

 Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas:

Controller → Maneja las peticiones HTTP (REST)

Service → Contiene la lógica de negocio

Repository → Acceso a base de datos con JPA

Model (Entity) → Entidades mapeadas a la base de datos

Enum → Estados del sistema (pedido, producto, restaurante)      

-------------------------------------------------------------------------

⚙️ Tecnologías Utilizadas:

Java 21

Spring Boot

Spring Web

Spring Data JPA

Hibernate

Jackson

MySQL / H2

Maven

--------------------------------------------------------------------------------

📦 Entidades Principales
👤 Usuario


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

🔁 Estados (Enums)
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
📌 Usuarios
POST   /api/usuarios
GET    /api/usuarios
GET    /api/usuarios/{id}
DELETE /api/usuarios/{id}

📌 Restaurantes
POST   /api/restaurantes
GET    /api/restaurantes
GET    /api/restaurantes/{id}
DELETE /api/restaurantes/{id}

📌 Productos
POST   /api/productos
GET    /api/productos
GET    /api/productos/{id}
DELETE /api/productos/{id}

📌 Repartidores
POST   /api/repartidores
GET    /api/repartidores
GET    /api/repartidores/{id}
DELETE /api/repartidores/{id}

📌 Pedidos
POST   /api/pedidos
GET    /api/pedidos
GET    /api/pedidos/{id}
DELETE /api/pedidos/{id}
----------------------------------------------------------------------------------------
🧾 Ejemplo de JSON para crear un Pedido
{
  "fecha": "2026-01-21",
  "total": 25000,
  "estadoPedido": "PENDIENTE",
  "clienteId": 1,
  "repartidorId": 2,
  "restauranteId": 3
}
----------------------------------------------------------------------------------------------------------
🔄 Manejo de Relaciones y JSON

Para evitar ciclos infinitos al listar información, se utilizan:

@JsonManagedReference

@JsonBackReference

@JsonIgnore
-------------------------------------------------------------------------------

🚀 Cómo ejecutar el proyecto

Clonar el repositorio

git clone https://github.com/tu-usuario/DeliveryApp.git


Abrir el proyecto en IntelliJ IDEA o VS Code

Configurar la base de datos en application.properties

Ejecutar la clase principal:

DeliveryAppApplication.java

---------------------------------------------------------------------------------------

Probar los endpoints con Postman o Insomnia

--------------------------------------------------------------------------------------

🎯 Objetivo del Proyecto

Este proyecto fue desarrollado con el objetivo de:

Practicar desarrollo backend con Spring Boot

Implementar APIs REST

Manejar relaciones JPA

Aplicar buenas prácticas de programación

-------------------------------------------------------------------------------------
🔁 Estados (Enums)
EstadoPedido

PENDIENTE,
EN_PREPARACION,
EN_CAMINO,
ENTREGADO,
CANCELADO

------------------------------------------------------------------------------------
## Endpoints principales

- /api/usuarios
- /api/restaurantes
- /api/productos
- /api/repartidores
- /api/pedidos


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
- Roles y permisos
- Paginación
- Validaciones con Bean Validation
- Documentación con Swagger


 Proyecto desarrollado con fines educativos y de portafolio.
 Autor Adalberto








