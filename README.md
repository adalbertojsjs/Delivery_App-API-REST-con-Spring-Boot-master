# DeliveryApp – API REST con Spring Boot y Arquitectura Hexagonal

DeliveryApp es una API REST hecha con Java 21 y Spring Boot 3.x que simula el funcionamiento básico de una aplicación de entrega de comida (tipo Uber Eats o Rappi). 

Creé este proyecto para practicar desarrollo backend, manejo de relaciones complejas en bases de datos y, principalmente, para aprender a estructurar el código usando Arquitectura Hexagonal.

---

## Cómo organicé el proyecto (Arquitectura)

Decidí usar Arquitectura Hexagonal para separar la lógica del negocio de las herramientas externas (como la base de datos o el propio Spring Boot). El código está dividido en tres partes:

* **Dominio**: Es el núcleo del proyecto. Aquí están las reglas del negocio y las entidades básicas. Es Java puro.
* **Puertos**: Son interfaces que definen qué acciones se pueden hacer y qué datos se necesitan para conectar el dominio con el exterior.
* **Adaptadores**: Es la implementación real de las tecnologías. Aquí están los controladores de Spring (para recibir las peticiones HTTP) y los repositorios de JPA (para guardar la información en MySQL).

### Nota sobre el manejo de datos y bucles infinitos
Para evitar errores de recursividad o bucles infinitos al consultar datos relacionados, separé las entidades de la base de datos de los datos que viajan por la red. Uso DTOs (Data Transfer Objects) y clases mapeadoras para transformar la información entre capas de forma limpia.

---

## Tecnologías utilizadas

* Java 21
* Spring Boot 3.x (Spring Web, Spring Data JPA)
* Hibernate y MySQL / H2
* Maven
* Validaciones con Jakarta Bean Validation (@Valid, @NotNull, @NotBlank)

---

## Entidades Principales

* **User (Usuario)**: Clientes que piden comida y repartidores que la entregan.
* **Restaurant (Restaurante)**: Locales comerciales con sus datos y estado (OPEN / CLOSED).
* **Product (Producto)**: Comida o artículos de un restaurante con estado de disponibilidad.
* **Order (Pedido)**: Une al cliente, al restaurante y al repartidor, y maneja el estado de la orden.

### Estados del pedido (OrderStatus)
CREADO -> PAGADO -> ACEPTADO -> EN_PREPARACION -> EN_CAMINO -> ENTREGADO

---

## Rutas de la API (Endpoints)

Todas las rutas usan el prefijo /api/v2/ y siguen el estándar REST.

### Usuarios
* POST /api/v2/usuarios - Crear usuario
* GET  /api/v2/usuarios - Ver todos los usuarios
* GET  /api/v2/usuarios/{id} - Ver un usuario específico

### Pedidos
* POST /api/v2/pedidos - Crear un pedido nuevo
* GET  /api/v2/pedidos - Ver historial de pedidos
* GET  /api/v2/pedidos/{id} - Ver estado de un pedido

(Hice rutas similares para los productos y los restaurantes).

### Ejemplo de JSON para crear un Pedido (POST /api/v2/pedidos)

```json
{
  "clienteId": 12,
  "restauranteId": 2,
  "total": 25000.00
}
```

---

## Cómo correr el proyecto en tu computadora

### Pre requisitos
* Tener instalado JDK 21 y Maven.
* Tener MySQL corriendo

### 1. Clonar el repositorio
```bash
git clone https://github.com
cd DeliveryApp
```

### 2. Configurar la base de datos (src/main/resources/application.properties)
Asegúrate de cambiar los datos de acceso por los tuyos:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/delivery_app?createDatabaseIfNotExist=true
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contrasenia
spring.jpa.hibernate.ddl-auto=update
```

### 3. Ejecutar
```bash
mvn clean spring-boot:run
```
La aplicación se levantará en el puerto http://localhost:8080.

---

## Cosas que quiero agregarle después (Mejoras futuras)
* Agregar seguridad con Spring Security y tokens JWT.
* Paginación en las listas de usuarios y pedidos para no saturar la memoria.
* Subir el proyecto a Docker para que sea más fácil de desplegar.
  
---
**Desarrollado por:** Adalberto — Programador Backend enfocado en aprender buenas prácticas y código limpio.
