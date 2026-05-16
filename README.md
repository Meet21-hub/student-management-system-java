# 🎓 Student Management System (Java Full-Stack Enterprise)

## 📌 Description
A fully functional, enterprise-grade **Student Management System** upgraded from a desktop Swing application to a modern, responsive web application. 
This project is built using the Java Full-Stack architecture (Servlets, JSP, JDBC, MySQL) and features a professional HTML5/CSS3/JavaScript frontend with Bootstrap 5.

---

## 🚀 Features

### 📋 Core Features
- ➕ **Add Student:** Register new students with academic & personal details.
- 👁️ **View Students:** Read all students with a responsive, paginated data table.
- 🔍 **Search Student:** Dynamic search by name or ID.
- ✏️ **Update Student:** Edit existing student records seamlessly.
- 🗑️ **Delete Student:** Secure deletion with confirmation modals.

### 🛡️ Advanced Features
- **Authentication:** Secure Admin login with server-side session management and 30-minute timeouts.
- **Enterprise UI:** Corporate dashboard built with Bootstrap 5, FontAwesome icons, and semantic HTML5.
- **Data Persistence:** Integrated with MySQL via JDBC using the Data Access Object (DAO) pattern.
- **Pagination:** Server-side pagination limiting records to 8 per page for performance.
- **Validation:** Both client-side (HTML5/JS) and server-side validation.

---

## 💻 Technologies Used

**Frontend:**
- HTML5 & CSS3
- JavaScript
- JSP (JavaServer Pages)
- Bootstrap 5
- FontAwesome 6

**Backend:**
- Java 17+
- Java Servlets (Tomcat 9 compatible)
- JDBC (Java Database Connectivity)
- Maven

**Database:**
- MySQL 8.x

---

## ⚙️ Setup & Deployment

1. **Database:** 
   - Execute the SQL script located in `sql/schema.sql` on your MySQL server to create the `student_management_db` and necessary tables.
   - Default admin credentials: `admin` / `admin123`.

2. **Configuration:**
   - Update `src/main/java/com/sms/util/DBConnection.java` with your MySQL credentials if they differ from `root` / `root`.

3. **Build:**
   - Package the project using Maven: `mvn clean package`

4. **Deploy:**
   - Deploy the generated `StudentManagementSystem.war` to Apache Tomcat 9.
   - Access the application at `http://localhost:8080/StudentManagementSystem`.
