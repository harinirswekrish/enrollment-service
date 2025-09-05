# Enrollment Service – Mini School ERP  

## 📌 Overview  
The **Enrollment Service** is a microservice in the **Mini School ERP System** responsible for managing student-course enrollments. It ensures that students can be enrolled in courses, and also allows admins to revert enrollments when necessary.  

This service integrates with other microservices (**Student Service** and **Course Service**) and communicates securely using **JWT authentication** (validated via Redis).  

---

## 🚀 Features  
- **Enroll student in a course** (Admin only)  
- **Get all enrollments for a student** (Admin & Staff)  
- **Revert enrollment for a student** (Admin only)  
- **Secure API access** using **JWT + Redis**  
- **Validation & Error Handling** with meaningful response codes  

---

## 🛠️ Tech Stack  
- **Java 17** (Java 8+ supported)  
- **Spring Boot 3.x**  
- **Spring Data JPA (Hibernate)**  
- **PostgreSQL**  
- **Redis (for JWT blacklisting/session mgmt)**  
- **Spring Security with JWT**  
- **Swagger/OpenAPI**  
- **Maven**  

---

## 🏗️ Architecture & Flow
1. **API Gateway** validates JWT and routes requests.  
2. Requests reach **Enrollment Service** running on port `8084`.  
3. Enrollment Service interacts with **PostgreSQL** for persistence.  
4. Responses are returned in **JSON** with proper HTTP status codes.  

---

## 🗄️ Database Schema
**Table: enrollments**

| Column                          | Type                            | Constraints                               |
| ------------------------------- | ------------------------------- | ----------------------------------------- |
| id                              | BIGSERIAL PK                    | Primary Key                               |
| student\_id                     | BIGINT FK                       | REFERENCES students(id)                   |
| course\_id                      | BIGINT FK                       | REFERENCES courses(id)                    |
| enrolled\_at                    | TIMESTAMP                       | DEFAULT now()                             |
| status                          | VARCHAR(20)                     | Default `ACTIVE`, can be `REVERTED` later |
| UNIQUE(student\_id, course\_id) | Ensures no duplicate enrollment |                                           |


---

## 🚀 Setup & Run

### Prerequisites
- JDK 8+  
- Maven 3+  
- PostgreSQL  

### Steps
```bash
# Clone repository
git clone https://github.com/harinirswekrish/course-service.git
cd course-service

# Update application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=yourpassword

jwt.secret=yourSecretKey
jwt.expiration=3600000
