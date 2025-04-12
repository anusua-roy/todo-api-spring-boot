# 📝 Spring Boot TODO API

A basic RESTful TODO application built with **Spring Boot** for learning and demonstrating backend API development.

---

## ⚙️ Features
- Create, Read, Update, Delete (CRUD) operations for TODOs
- In-memory database (H2 or simple list-based storage)
- REST endpoints following standard API conventions
- Clean folder structure and controller-service-repository pattern

---

## 🔧 Tech Stack
- Java 17+
- Spring Boot
- Maven
- Spring Web
- (Optional) Spring Data JPA

---

## 🚀 Running Locally

```bash
# Clone the repo
git clone https://github.com/anusua-roy/todo-api-spring-boot.git
cd todo-api-spring-boot

# Run the app
./mvnw spring-boot:run
```

Visit: `http://localhost:8080/api/todos`

---

## 📬 API Endpoints
| Method | Endpoint             | Description            |
|--------|----------------------|------------------------|
| GET    | `/api/todos`         | List all TODOs         |
| POST   | `/api/todos`         | Add new TODO           |
| GET    | `/api/todos/{id}`    | Get TODO by ID         |
| PUT    | `/api/todos/{id}`    | Update TODO by ID      |
| DELETE | `/api/todos/{id}`    | Delete TODO by ID      |

---

## 📁 Structure
```
├── src
│   └── main
│       ├── java
│       │   └── com.example.todo
│       │       ├── controller
│       │       ├── service
│       │       └── model
│       └── resources
│           └── application.properties
└── pom.xml
```

---

## 🛠 Future Enhancements
- Add Swagger UI for API testing
- Switch to persistent DB like MySQL/Postgres
- Add authentication layer

---

## 📄 License
Open source under the [MIT License](LICENSE).
