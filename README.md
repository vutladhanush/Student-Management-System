# 🎓 Student Management System (REST API)

A REST API built with **Java, Spring Boot and MySQL** to manage students, courses and enrollments.

---

## 🚀 Features

* CRUD operations for **Students** and **Courses**
* **Enroll / unenroll** a student in a course (many-to-many relationship)
* View all courses of a student and all students of a course
* **Search**, **pagination** and **sorting** on students and courses
* Input validation (`@Valid`, `@NotBlank`, `@Email`, `@Pattern`)
* Global exception handling (`@RestControllerAdvice`) with proper 400 / 404 / 409 responses
* DTOs used for requests and responses (entities are never exposed)
* Layered architecture: Controller → Service → Repository

---

## 🛠️ Tech Stack

* Java 17, Spring Boot 3
* Spring Data JPA & Hibernate
* MySQL
* Apache Maven
* Postman (API testing)

---

## 📐 Database Design

```text
student (id, name, email, phone)
course  (id, code, title, description, credits)
student_course (student_id FK, course_id FK)   <- join table for many-to-many
```

---

## 🌐 API Endpoints

### Students

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| POST | `/api/students` | Create a student |
| GET | `/api/students` | List students (search, pagination, sorting) |
| GET | `/api/students/{id}` | Get a student by id |
| PUT | `/api/students/{id}` | Update a student |
| DELETE | `/api/students/{id}` | Delete a student |

Query params for the list: `name`, `page` (default 0), `size` (default 10), `sortBy` (default id), `direction` (asc/desc).
Example: `/api/students?name=ra&page=0&size=5&sortBy=name&direction=desc`

### Courses

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| POST | `/api/courses` | Create a course |
| GET | `/api/courses` | List courses (search by `title`, pagination, sorting) |
| GET | `/api/courses/{id}` | Get a course by id |
| PUT | `/api/courses/{id}` | Update a course |
| DELETE | `/api/courses/{id}` | Delete a course |
| GET | `/api/courses/{courseId}/students` | Students enrolled in a course |

### Enrollment

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| POST | `/api/students/{studentId}/courses/{courseId}` | Enroll a student in a course |
| DELETE | `/api/students/{studentId}/courses/{courseId}` | Remove a student from a course |
| GET | `/api/students/{studentId}/courses` | Courses of a student |

### Sample request bodies

`POST /api/students`
```json
{
  "name": "Ravi Kumar",
  "email": "ravi@example.com",
  "phone": "9876543210"
}
```

`POST /api/courses`
```json
{
  "code": "CS101",
  "title": "Java Programming",
  "description": "Core Java and OOP",
  "credits": 4
}
```

### Sample error response (validation)
```json
{
  "timestamp": "2026-11-01T10:15:30",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "fieldErrors": { "email": "Email must be valid" }
}
```

---

## 💻 Run Locally

### Prerequisites
* JDK 17+
* Maven
* MySQL Server running on port 3306

### Steps
1. Clone the repo and open it in IntelliJ IDEA.
2. Set your MySQL username and password (environment variables `DB_USERNAME` and `DB_PASSWORD`, or edit `src/main/resources/application.properties`). The database `student_db` is created automatically.
3. Run:
```bash
mvn spring-boot:run
```
4. The API runs at `http://localhost:8080`. Test it with Postman.

---

## 📸 Postman Screenshots

Add your screenshots to a `screenshots/` folder and link them here.

---

## 👤 Author

**Vutla Dhanush**
🎓 B.Tech – Computer Science and Engineering, KL University
🌐 [GitHub Profile](https://github.com/vutladhanush)
