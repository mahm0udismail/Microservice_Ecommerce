# 🛒 Microservice E-Commerce

A scalable, cloud-native **Order Management System** built with **Spring Boot 3** and **Spring Cloud** using a microservices architecture. Services communicate synchronously via **OpenFeign** (no Kafka).

---

## 🏗️ Architecture

```
Client / Postman
      │
      ▼
┌─────────────────┐
│   API Gateway   │  :8222  (Spring Cloud Gateway + Keycloak JWT)
└────────┬────────┘
         │  routes via Eureka service discovery
    ┌────┴────┬──────────┬──────────┐
    ▼         ▼          ▼          ▼
Customer   Product    Order     Payment
Service    Service    Service   Service
 :8090      :8050      :8070     :8060
MongoDB   PostgreSQL PostgreSQL PostgreSQL
                        │          │
                   Feign calls     │
                        └──────────┼──► Notification Service :8040
                                         MongoDB + MailDev
```

### Service Communication (Feign REST — no Kafka)

| Caller | Calls | Purpose |
|---|---|---|
| Order Service | Customer Service | Validate customer exists |
| Order Service | Product Service | Purchase products / reduce stock |
| Order Service | Payment Service | Process payment |
| Order Service | Notification Service | Send order confirmation email |
| Payment Service | Notification Service | Send payment confirmation email |

---

## 🛠️ Technologies

| Technology | Purpose |
|---|---|
| Spring Boot 3 | Microservice framework |
| Spring Cloud Gateway | API Gateway and routing |
| Spring Cloud Config | Centralized configuration |
| Eureka Server | Service discovery and registration |
| OpenFeign | Synchronous inter-service REST calls |
| PostgreSQL | Relational data (products, orders, payments) |
| MongoDB | Document data (customers, notifications) |
| Flyway | Database schema migration |
| Keycloak | Authentication & JWT authorization |
| Zipkin | Distributed tracing |
| MailDev | Local email testing |
| Docker & Docker Compose | Containerization |
| Thymeleaf | HTML email templates |

---

## 📦 Microservices

| Service | Port | Database | Description |
|---|---|---|---|
| Config Server | 8888 | — | Serves config to all services |
| Eureka Server | 8761 | — | Service registry |
| API Gateway | 8222 | — | Single entry point, JWT validation |
| Customer Service | 8090 | MongoDB | CRUD for customers and addresses |
| Product Service | 8050 | PostgreSQL | Product catalog + stock management |
| Order Service | 8070 | PostgreSQL | Order creation and orchestration |
| Payment Service | 8060 | PostgreSQL | Payment processing |
| Notification Service | 8040 | MongoDB | Email notifications via MailDev |

---

## 🚀 Getting Started

### Prerequisites

- Docker Desktop (or Docker + Docker Compose)
- Java 17+
- Maven 3.8+

### 1. Clone the repository

```bash
git clone https://github.com/mahm0udismail/Microservice_Ecommerce.git
cd Microservice_Ecommerce
```

### 2. Start all infrastructure and services

```bash
docker-compose up --build -d
```

Wait about 60–90 seconds for all services to start up in order.

### 3. Verify everything is running

| UI | URL |
|---|---|
| Eureka Dashboard | http://localhost:8761 |
| Zipkin Tracing | http://localhost:9411 |
| MailDev (emails) | http://localhost:1080 |
| pgAdmin | http://localhost:5050 |
| Mongo Express | http://localhost:8081 |
| Keycloak Admin | http://localhost:9090 (admin/admin) |

---

## 🔐 Keycloak Setup (one-time)

1. Open http://localhost:9090 and log in with `admin / admin`
2. Create a new **Realm** named `ecommerce`
3. Create a **Client** named `ecommerce-client` (Client authentication: ON)
4. Under the client, go to **Credentials** and copy the client secret
5. To get a token for Postman:

```
POST http://localhost:9090/realms/ecommerce/protocol/openid-connect/token
Content-Type: application/x-www-form-urlencoded

grant_type=client_credentials
&client_id=ecommerce-client
&client_secret=<your-secret>
```

Use the returned `access_token` as a Bearer token in all API Gateway requests.

---

## 📬 API Endpoints (via Gateway on port 8222)

### Customer Service

```
POST   /api/v1/customers          Create customer
GET    /api/v1/customers          List all customers
GET    /api/v1/customers/{id}     Get customer by ID
PUT    /api/v1/customers          Update customer
DELETE /api/v1/customers/{id}     Delete customer
```

**Create Customer example:**
```json
POST http://localhost:8222/api/v1/customers
{
  "firstname": "John",
  "lastname": "Doe",
  "email": "john.doe@example.com",
  "address": {
    "street": "123 Main St",
    "houseNumber": "4B",
    "zipCode": "10001"
  }
}
```

### Product Service

```
GET    /api/v1/products           List all products
GET    /api/v1/products/{id}      Get product by ID
POST   /api/v1/products           Create product
POST   /api/v1/products/purchase  Purchase products (internal use by Order Service)
```

### Order Service

```
POST   /api/v1/orders             Create order
GET    /api/v1/orders             List all orders
GET    /api/v1/orders/{id}        Get order by ID
```

**Create Order example:**
```json
POST http://localhost:8222/api/v1/orders
{
  "reference": "ORD-2024-001",
  "amount": 1349.98,
  "paymentMethod": "CREDIT_CARD",
  "customerId": "<customer-id-from-step-above>",
  "products": [
    { "productId": 1, "quantity": 1 },
    { "productId": 5, "quantity": 1 }
  ]
}
```

When an order is created:
1. Customer is validated via Feign
2. Stock is purchased/reduced via Feign
3. Payment is processed via Feign
4. Order confirmation email is sent via Feign → check http://localhost:1080
5. Payment confirmation email is sent → check http://localhost:1080

---

## 📁 Project Structure

```
Microservice_Ecommerce/
├── config-server/           # Spring Cloud Config Server
├── EurekaServer/            # Netflix Eureka service registry
├── api-gateway/             # Spring Cloud Gateway + Keycloak security
├── customer-service/        # Customer CRUD (MongoDB)
├── product-service/         # Product catalog + purchase endpoint (PostgreSQL)
├── order-service/           # Order orchestration with 4 Feign clients (PostgreSQL)
├── payment-service/         # Payment processing (PostgreSQL)
├── notification-service/    # Email notifications with Thymeleaf (MongoDB + MailDev)
├── docker/
│   └── postgres/
│       └── init.sql         # Creates product_db, order_db, payment_db
└── docker-compose.yml       # Full stack definition
```

---

## 📊 Domain Model

### Customer Domain (MongoDB)
- `Customer` → `id`, `firstname`, `lastname`, `email`
- `Address` → `street`, `houseNumber`, `zipCode`

### Product Domain (PostgreSQL)
- `Product` → `id`, `name`, `description`, `availableQuantity`, `price`
- `Category` → `id`, `name`, `description`

### Order Domain (PostgreSQL)
- `Order` → `id`, `reference`, `totalAmount`, `paymentMethod`, `customerId`
- `OrderLine` → `id`, `orderId`, `productId`, `quantity`

### Payment Domain (PostgreSQL)
- `Payment` → `id`, `reference`, `amount`, `paymentMethod`, `orderId`

### Notification Domain (MongoDB)
- `Notification` → `id`, `type`, `orderReference`, `recipientEmail`, `content`, `date`

---

## 📬 Contact

Feel free to reach out for questions or collaboration.
