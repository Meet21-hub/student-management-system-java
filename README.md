# 🎓 Student Management System — Full Stack Web App

> **Java Servlets + JDBC + MySQL + Bootstrap 5**
> Upgraded from a CLI/Swing app to a professional placement-ready full-stack web application.

[![Java](https://img.shields.io/badge/Java-17-orange?logo=java)](https://www.java.com)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-blue?logo=mysql)](https://www.mysql.com)
[![Servlet](https://img.shields.io/badge/Servlet-4.0-green)](https://jakarta.ee)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📌 Project Overview

A complete **web-based Student Management System** built with:
- **Frontend:** HTML5, CSS3 (custom dark theme), Vanilla JavaScript, Bootstrap 5
- **Backend:** Java Servlets (MVC pattern)
- **Database:** MySQL via JDBC (DAO pattern)
- **Server:** Apache Tomcat 9+

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🔐 Admin Login | Secure session-based authentication |
| 🏠 Dashboard | Stats cards + recently added students |
| 👨‍🎓 View Students | Paginated table with search |
| ➕ Add Student | Form with client + server validation |
| ✏️ Edit Student | Pre-populated update form |
| 🗑️ Delete Student | Confirmation modal before delete |
| 🔍 Search | Search by name or student ID |
| 📄 Pagination | 8 students per page |
| 🚪 Logout | Session invalidation |
| 📱 Responsive | Works on desktop and mobile |

---

## 🗂️ Project Structure

```
StudentManagementSystem/
├── pom.xml                        # Maven build configuration
├── sql/
│   └── schema.sql                 # Database schema + sample data
├── src/
│   └── main/java/com/sms/
│       ├── model/
│       │   └── Student.java       # Student POJO
│       ├── dao/
│       │   ├── StudentDAO.java    # CRUD database operations
│       │   └── AdminDAO.java      # Admin authentication
│       ├── servlet/
│       │   ├── LoginServlet.java  # GET/POST login
│       │   ├── LogoutServlet.java # Session invalidation
│       │   ├── DashboardServlet.java
│       │   └── StudentServlet.java # Master CRUD servlet
│       └── util/
│           └── DBConnection.java  # JDBC singleton connection
└── webapp/
    ├── WEB-INF/
    │   └── web.xml               # Deployment descriptor
    ├── css/
    │   └── style.css             # Dark theme CSS
    ├── js/
    │   └── app.js                # Frontend JavaScript
    ├── includes/
    │   ├── sidebar.jsp           # Reusable sidebar
    │   └── delete-modal.jsp      # Delete confirmation modal
    ├── index.jsp                 # Redirects to /login
    ├── login.jsp                 # Admin login page
    ├── dashboard.jsp             # Dashboard page
    ├── students.jsp              # Students list + search
    ├── student-form.jsp          # Add / Edit student form
    └── error.jsp                 # 404 / 500 error page
```

---

## 🛠️ Prerequisites

| Tool | Version |
|------|---------|
| JDK | 17+ |
| Apache Maven | 3.8+ |
| Apache Tomcat | 9.x or 10.x |
| MySQL Server | 8.x |
| IDE | IntelliJ IDEA / Eclipse / NetBeans |

---

## 🚀 Setup & Installation

### Step 1 — Clone / Import
```bash
git clone https://github.com/YOUR_USERNAME/StudentManagementSystem.git
cd StudentManagementSystem
```

### Step 2 — Configure MySQL
1. Open MySQL Workbench or MySQL CLI
2. Run the schema file:
```sql
source sql/schema.sql;
```
This creates:
- `student_management_db` database
- `admin_users` table (default: `admin` / `admin123`)
- `students` table with 8 sample records

### Step 3 — Configure Database Connection
Edit `src/main/java/com/sms/util/DBConnection.java`:
```java
private static final String DB_URL      = "jdbc:mysql://localhost:3306/student_management_db";
private static final String DB_USER     = "root";       // ← your MySQL username
private static final String DB_PASSWORD = "root";       // ← your MySQL password
```

### Step 4 — Build with Maven
```bash
mvn clean package
```
This generates: `target/StudentManagementSystem.war`

### Step 5 — Deploy to Tomcat
1. Copy `StudentManagementSystem.war` → `TOMCAT_HOME/webapps/`
2. Start Tomcat: `TOMCAT_HOME/bin/startup.bat` (Windows)

### Step 6 — Open in Browser
```
http://localhost:8080/StudentManagementSystem/
```

**Default Credentials:**
| Username | Password |
|----------|----------|
| admin | admin123 |

---

## 🗃️ Database Schema

```sql
-- Students table
CREATE TABLE students (
    student_id  INT AUTO_INCREMENT PRIMARY KEY,
    full_name   VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL UNIQUE,
    phone       VARCHAR(15)  NOT NULL,
    department  VARCHAR(100) NOT NULL,
    year        TINYINT      NOT NULL,  -- 1 to 4
    cgpa        DECIMAL(4,2) NOT NULL,  -- 0.00 to 10.00
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Admin users table
CREATE TABLE admin_users (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    username   VARCHAR(50)  NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 🔌 API Routes (Servlet Mappings)

| URL | Method | Description |
|-----|--------|-------------|
| `/login` | GET | Show login page |
| `/login` | POST | Authenticate admin |
| `/logout` | GET | Destroy session |
| `/dashboard` | GET | Dashboard with stats |
| `/students?action=list` | GET | View all students |
| `/students?action=list&search=X` | GET | Search students |
| `/students?action=add` | GET | Show add form |
| `/students?action=add` | POST | Insert new student |
| `/students?action=edit&id=X` | GET | Show edit form |
| `/students?action=edit` | POST | Update student |
| `/students?action=delete&id=X` | GET | Delete student |

---

## 📸 Screenshots

| Page | Description |
|------|-------------|
| `screenshots/login.png` | Admin login page |
| `screenshots/dashboard.png` | Dashboard with stat cards |
| `screenshots/students.png` | Students table with search |
| `screenshots/add-student.png` | Add student form |
| `screenshots/edit-student.png` | Edit student form |

> 📷 Add screenshots to `webapp/images/screenshots/` directory

---

## 🧩 MVC Architecture

```
Browser → Servlet (Controller) → DAO (Model) → MySQL
                ↓
            JSP (View)
```

- **Model:** `Student.java`, `AdminDAO.java`, `StudentDAO.java`
- **View:** JSP pages (`login.jsp`, `dashboard.jsp`, `students.jsp`, `student-form.jsp`)
- **Controller:** Servlets (`LoginServlet`, `DashboardServlet`, `StudentServlet`)

---

## 🔐 Security Notes

- Session-based authentication (30-min timeout)
- All DB queries use `PreparedStatements` (SQL injection prevention)
- Server-side + client-side form validation
- Session guard on every protected page

> ⚠️ **For production:** Use BCrypt password hashing instead of plain text.

---

## 📦 Technologies Summary

| Layer | Technology |
|-------|-----------|
| Frontend | HTML5, CSS3, Vanilla JS |
| Styling | Custom dark CSS + Google Fonts |
| Backend | Java 17, Servlets 4.0 |
| Data Access | JDBC, DAO Pattern |
| Database | MySQL 8.x |
| Build | Maven 3.8+ |
| Server | Apache Tomcat 9+ |

---

## 👤 Author

**Khayam** | Java Full Stack Developer
- GitHub: [@Meet21-hub](https://github.com/Meet21-hub)

---

## 📄 License

This project is licensed under the MIT License.
