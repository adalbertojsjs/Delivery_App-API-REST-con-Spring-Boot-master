# DeliveryApp – REST API with Spring Boot

DeliveryApp is a REST API developed with Spring Boot that simulates the basic functionality of a food delivery application.  
It allows the management of users (customers), restaurants, products, delivery drivers, and orders, handling real relationships between entities.

The project was created as a backend practice project and portfolio piece, applying good development practices.

---

# Project Architecture

The project follows a Hexagonal Architecture.

---

# Technologies Used

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- JSON
- MySQL / H2
- Maven

---

# Main Entities

## User

Represents the customers of the system.

### User Roles

The system handles the following roles:

- CUSTOMER → Places orders
- DELIVERY_DRIVER → Delivers orders
- RESTAURANT → Manages products (future implementation)

---

## Restaurant

Contains basic information and a list of associated products.

---

## Product

Belongs to a restaurant and has an availability status.

---

## Delivery Driver

User responsible for delivering orders.

---

## Order

Main entity that relates:

- Customer
- Delivery Driver
- Restaurant

Includes:

- Date
- Total amount

---

# Status Enums

## OrderStatus

```java

    CREADO,
    ACEPTADO,
    EN_PREPARACION,
    EN_CAMINO,
    ENTREGADO,
    CANCELADO,
    PAGADO,
    PAGO_RECHAZADO
```

## ProductAvailability

```java
AVAILABLE,
NOT_AVAILABLE
```

## RestaurantStatus

```java
OPEN,
CLOSED
```

---

# Main Endpoints (URLs)

## Users

```http
POST   /api/v2/usuarios
GET    /api/v2/usuarios
GET    /api/v2/{id}
```

## Restaurants

```http
POST   /api/v2/restaurantes
GET    /api/v2/restaurantes
DELETE /api/v2/{id}
```

## Products

```http
POST   /api/v2/productos
GET    /api/v2/productos
GET    /api/v2/productos/{id}
DELETE /api/v2/productos/{id}
```

## Delivery Drivers

```http
POST   /api/v2/repartidor
GET    /api/v2/{id}
```

## Orders

```http
POST   /api/v2/pedidos
GET    /api/v2/pedidos
GET    /api/v2/pedidos/{id}
DELETE /api/v2/pedidos/{id}
```

---

# Example JSON to Create an Order

```json
Ejemplo de JSON para crear un Pedido
{
  "clienteId": 12,
  "repartidorId": null,
 "restauranteId": 2,
  "total": 25000.00
}
```

---

# Relationship and JSON Handling

To avoid infinite loops when listing information, the project uses:

- `@JsonManagedReference`
- `@JsonBackReference`
- `@JsonIgnore`

---

# How to Run the Project

## Clone the Repository

```bash
git clone https://github.com/your-username/DeliveryApp.git
```

## Open the Project

Open the project in IntelliJ IDEA or VS Code.

## Configure the Database

Set up the database in `application.properties`.

## Run the Main Class

```java
DeliveryAppApplication.java
```

---

# Testing the API

Test the endpoints using Postman or Insomnia.

---

# Project Goals

This project was developed with the purpose of:

- Practicing backend development with Spring Boot
- Implementing REST APIs
- Managing JPA relationships
- Applying good programming practices

---

# Main Endpoints

- `/api/v2/usuarios`
- `/api/v2/restaurantes`
- `/api/v2/productos`
- `/api/v2/pedidos`

---

# Database Configuration

The project uses MySQL or H2.

Example in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/delivery_app
spring.datasource.username=root
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

# Future Improvements

- JWT Authentication
- Pagination

---

Project developed for educational and portfolio purposes.  
Author: Adalberto
