# Employee & Task Management REST API

A production-style backend REST API built with **Java 17, Spring Boot 3, Spring Data JPA, Hibernate, MySQL/H2, and Swagger UI**.

Designed with a clean **Layered Architecture (Controller → Service → Repository → Database)** with strict DTO separation, request validation, and centralized exception handling.

---

## 🌟 Features

- **Layered Architecture**: Decoupled Controller, Service, Repository, and Entity layers.
- **Relational Data Modeling**:
  - `Department` $\leftrightarrow$ `Employee` (One-to-Many / Many-to-One)
  - `Employee` $\leftrightarrow$ `Task` (One-to-Many / Many-to-One)
- **DTOs & Data Integrity**: Entities are never exposed directly to clients, preventing Jackson infinite recursion (`StackOverflowError`) and over-posting vulnerabilities.
- **Request Validation**: Automatic input validation using Jakarta Validation (`@NotBlank`, `@Email`, `@NotNull`, `@Positive`, `@FutureOrPresent`).
- **Centralized Exception Handling**: `@RestControllerAdvice` translates internal exceptions into clean, structured JSON errors (`400 Bad Request`, `404 Not Found`, `409 Conflict`, `500 Server Error`).
- **Pagination & Sorting**: Built-in `Pageable` query support on employee listings (`/api/employees?page=0&size=10&sortBy=salary&direction=desc`).
- **Interactive API Documentation**: Live Swagger OpenAPI UI at `http://localhost:8080/swagger-ui.html`.
- **Automatic Data Seeding**: Pre-loaded with initial sample departments, employees, and tasks via `CommandLineRunner`.
- **Dual-Database Support**:
  - **H2 In-Memory DB** (Active by default for zero-config 1-click execution)
  - **MySQL** (Easily enabled in `application.properties`)

---

## 🏗️ Architecture & Database Design

### Entity Relationship Diagram
```text
 +---------------+          +---------------+          +---------------+
 |  DEPARTMENT   | 1      * |   EMPLOYEE    | 1      * |     TASK      |
 +---------------+----------+---------------+----------+---------------+
 | id (PK)       |          | id (PK)       |          | id (PK)       |
 | name          |          | firstName     |          | title         |
 | location      |          | lastName      |          | description   |
 +---------------+          | email (UQ)    |          | status        |
                            | designation   |          | priority      |
                            | salary        |          | dueDate       |
                            | department_id |          | employee_id   |
                            +---------------+          +---------------+
```

---

## 🚀 How to Run the Project

### Prerequisites
- **JDK 17 or higher**
- **Maven 3.8+** (or use IntelliJ / Eclipse / VS Code built-in runner)
- *(Optional)* MySQL Server 8.0+

### Option A: Run directly in IntelliJ IDEA / Eclipse / VS Code
1. Open this folder `employee-task-management-api` in your IDE.
2. Locate `src/main/java/com/example/employeetask/EmployeeTaskApiApplication.java`.
3. Right-click and choose **Run 'EmployeeTaskApiApplication'**.
4. The application will start at `http://localhost:8080`.

### Option B: Run via Terminal
```bash
mvn clean spring-boot:run
```

---

## 🧪 Testing the APIs

### 1. Interactive Swagger UI (Recommended)
Open your browser and navigate to:
👉 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

You can test every endpoint directly from the browser with sample payloads!

### 2. H2 Console
If running in H2 mode, view database tables at:
👉 **[http://localhost:8080/h2-console](http://localhost:8080/h2-console)**
- JDBC URL: `jdbc:h2:mem:empdb`
- Username: `sa`
- Password: *(leave blank)*

### 3. Postman Collection
Import the included `postman_collection.json` directly into Postman to execute pre-configured test requests.

---

## 🔌 API Endpoints Summary

### Departments (`/api/departments`)
| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/departments` | Create department | `201 Created` |
| `GET` | `/api/departments` | List all departments | `200 OK` |
| `GET` | `/api/departments/{id}` | Get department by ID | `200 OK` |
| `PUT` | `/api/departments/{id}` | Update department details | `200 OK` |
| `DELETE` | `/api/departments/{id}` | Delete department | `204 No Content` |

### Employees (`/api/employees`)
| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/employees` | Register new employee | `201 Created` |
| `GET` | `/api/employees` | Get employees (paged & sorted) | `200 OK` |
| `GET` | `/api/employees/all` | Get all employees (flat list) | `200 OK` |
| `GET` | `/api/employees/{id}` | Get employee profile by ID | `200 OK` |
| `GET` | `/api/employees/department/{deptId}` | Filter employees by department | `200 OK` |
| `PUT` | `/api/employees/{id}` | Update employee profile | `200 OK` |
| `DELETE` | `/api/employees/{id}` | Remove employee | `204 No Content` |

### Tasks (`/api/tasks`)
| Method | Endpoint | Description | Status Code |
| :--- | :--- | :--- | :--- |
| `POST` | `/api/tasks` | Create task & assign to employee | `201 Created` |
| `GET` | `/api/tasks` | List all tasks | `200 OK` |
| `GET` | `/api/tasks/{id}` | Get task by ID | `200 OK` |
| `GET` | `/api/tasks/employee/{empId}` | Get all tasks assigned to an employee | `200 OK` |
| `GET` | `/api/tasks/status/{status}` | Filter by status (`PENDING`, `IN_PROGRESS`, `COMPLETED`) | `200 OK` |
| `GET` | `/api/tasks/priority/{priority}` | Filter by priority (`LOW`, `MEDIUM`, `HIGH`) | `200 OK` |
| `PATCH`| `/api/tasks/{id}/status` | Update task progress status | `200 OK` |
| `PUT`  | `/api/tasks/{id}/assign/{empId}` | Reassign task to another employee | `200 OK` |
| `DELETE`| `/api/tasks/{id}` | Delete task | `204 No Content` |

---

## 💬 Top Interview Questions for this Project

### 1. Why did you use DTOs instead of returning JPA entities directly?
> *"Returning entities directly exposes our internal database schema, permits malicious client over-posting, and triggers Jackson JSON serialization infinite recursion (`StackOverflowError`) with bidirectional JPA relationships. DTOs decouple our API contract from our database tables."*

### 2. How did you handle validation and error responses?
> *"I used Jakarta Bean Validation on DTOs with annotations like `@NotBlank`, `@Email`, and `@Positive`. When validation fails, Spring throws `MethodArgumentNotValidException`. I centralized the handling of this and custom exceptions like `ResourceNotFoundException` using a `@RestControllerAdvice` class that formats all errors into a standardized `ErrorResponse` JSON with appropriate HTTP status codes."*

### 3. What fetch types did you use in your entity relationships and why?
> *"I used `FetchType.LAZY` on `@ManyToOne` relationships to prevent the N+1 query problem and avoid loading heavy relational data into memory unless explicitly queried."*

### 4. How did you handle database transactions?
> *"I used `@Transactional(readOnly = true)` at the service class level for read operations to optimize Hibernate dirty-checking, and method-level `@Transactional` on write operations (create, update, delete) to ensure ACID consistency."*

