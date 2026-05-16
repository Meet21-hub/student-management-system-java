package com.sms.servlet;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * StudentServlet — Master servlet for all Student CRUD operations.
 *
 * Routes by `action` request parameter:
 *
 *  GET  /students?action=list         → View all students (with optional search)
 *  GET  /students?action=add          → Show Add Student form
 *  POST /students?action=add          → Process Add Student form
 *  GET  /students?action=edit&id=X    → Show Edit form populated with student data
 *  POST /students?action=edit         → Process Update form
 *  GET  /students?action=delete&id=X  → Delete student (with confirmation handled by JS)
 *
 * All routes require an active admin session.
 */
@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    private final StudentDAO studentDAO = new StudentDAO();

    // ── Session Guard Helper ──────────────────────────────────────────────────
    private boolean isAuthenticated(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        return session != null && session.getAttribute("adminUser") != null;
    }

    // ─── GET Handler ─────────────────────────────────────────────────────────
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAuthenticated(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {

            case "list":
                handleList(req, resp);
                break;

            case "add":
                // Show empty add form
                req.getRequestDispatcher("/student-form.jsp").forward(req, resp);
                break;

            case "edit":
                handleEditForm(req, resp);
                break;

            case "delete":
                handleDelete(req, resp);
                break;

            default:
                resp.sendRedirect(req.getContextPath() + "/students?action=list");
        }
    }

    // ─── POST Handler ────────────────────────────────────────────────────────
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if (!isAuthenticated(req)) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String action = req.getParameter("action");
        if (action == null) action = "";

        switch (action) {
            case "add":    handleAdd(req, resp);    break;
            case "edit":   handleUpdate(req, resp); break;
            default:       resp.sendRedirect(req.getContextPath() + "/students?action=list");
        }
    }

    // ─── List / Search ───────────────────────────────────────────────────────
    private void handleList(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String search = req.getParameter("search");
        List<Student> students;

        if (search != null && !search.trim().isEmpty()) {
            students = studentDAO.searchStudents(search.trim());
            req.setAttribute("search", search.trim());
        } else {
            students = studentDAO.getAllStudents();
        }

        // ── Pagination ───────────────────────────────────────────────────────
        int pageSize = 8;
        int page     = 1;
        try {
            String pageParam = req.getParameter("page");
            if (pageParam != null) page = Integer.parseInt(pageParam);
            if (page < 1) page = 1;
        } catch (NumberFormatException ignored) {}

        int totalRecords = students.size();
        int totalPages   = (int) Math.ceil((double) totalRecords / pageSize);
        if (totalPages < 1) totalPages = 1;
        if (page > totalPages) page = totalPages;

        int from = (page - 1) * pageSize;
        int to   = Math.min(from + pageSize, totalRecords);
        List<Student> pageStudents = students.subList(from, to);

        req.setAttribute("students",     pageStudents);
        req.setAttribute("totalRecords", totalRecords);
        req.setAttribute("currentPage",  page);
        req.setAttribute("totalPages",   totalPages);

        req.getRequestDispatcher("/students.jsp").forward(req, resp);
    }

    // ─── Add Student ─────────────────────────────────────────────────────────
    private void handleAdd(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Student student = buildStudentFromRequest(req);

        // Server-side validation
        String validationError = validateStudent(student, null);
        if (validationError != null) {
            req.setAttribute("error", validationError);
            req.setAttribute("student", student);
            req.getRequestDispatcher("/student-form.jsp").forward(req, resp);
            return;
        }

        boolean success = studentDAO.addStudent(student);
        if (success) {
            req.getSession().setAttribute("successMsg", "Student added successfully! ✅");
            resp.sendRedirect(req.getContextPath() + "/students?action=list");
        } else {
            req.setAttribute("error", "Failed to add student. Email may already exist.");
            req.setAttribute("student", student);
            req.getRequestDispatcher("/student-form.jsp").forward(req, resp);
        }
    }

    // ─── Edit Form ───────────────────────────────────────────────────────────
    private void handleEditForm(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Student student = studentDAO.getStudentById(id);

            if (student == null) {
                req.getSession().setAttribute("errorMsg", "Student not found.");
                resp.sendRedirect(req.getContextPath() + "/students?action=list");
                return;
            }
            req.setAttribute("student", student);
            req.setAttribute("editMode", true);
            req.getRequestDispatcher("/student-form.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/students?action=list");
        }
    }

    // ─── Update Student ──────────────────────────────────────────────────────
    private void handleUpdate(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(req.getParameter("studentId"));
            Student student = buildStudentFromRequest(req);
            student.setStudentId(id);

            String validationError = validateStudent(student, id);
            if (validationError != null) {
                req.setAttribute("error", validationError);
                req.setAttribute("student", student);
                req.setAttribute("editMode", true);
                req.getRequestDispatcher("/student-form.jsp").forward(req, resp);
                return;
            }

            boolean success = studentDAO.updateStudent(student);
            if (success) {
                req.getSession().setAttribute("successMsg", "Student updated successfully! ✅");
            } else {
                req.getSession().setAttribute("errorMsg", "Failed to update student.");
            }
            resp.sendRedirect(req.getContextPath() + "/students?action=list");

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/students?action=list");
        }
    }

    // ─── Delete Student ──────────────────────────────────────────────────────
    private void handleDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean success = studentDAO.deleteStudent(id);

            if (success) {
                req.getSession().setAttribute("successMsg", "Student deleted successfully! 🗑️");
            } else {
                req.getSession().setAttribute("errorMsg", "Student not found.");
            }
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("errorMsg", "Invalid student ID.");
        }
        resp.sendRedirect(req.getContextPath() + "/students?action=list");
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    /** Builds a Student object from POST request parameters. */
    private Student buildStudentFromRequest(HttpServletRequest req) {
        Student s = new Student();
        s.setFullName(getParam(req, "fullName"));
        s.setEmail(getParam(req, "email"));
        s.setPhone(getParam(req, "phone"));
        s.setDepartment(getParam(req, "department"));

        try { s.setYear(Integer.parseInt(getParam(req, "year"))); }
        catch (NumberFormatException e) { s.setYear(0); }

        try { s.setCgpa(Double.parseDouble(getParam(req, "cgpa"))); }
        catch (NumberFormatException e) { s.setCgpa(0.0); }

        return s;
    }

    /** Returns trimmed request parameter or empty string if null. */
    private String getParam(HttpServletRequest req, String name) {
        String val = req.getParameter(name);
        return val != null ? val.trim() : "";
    }

    /** Basic server-side validation. Returns error message or null if valid. */
    private String validateStudent(Student s, Integer existingId) {
        if (s.getFullName().isEmpty())    return "Full name is required.";
        if (s.getEmail().isEmpty())       return "Email is required.";
        if (!s.getEmail().contains("@")) return "Invalid email format.";
        if (s.getPhone().isEmpty())       return "Phone number is required.";
        if (!s.getPhone().matches("\\d{10}")) return "Phone must be a 10-digit number.";
        if (s.getDepartment().isEmpty())  return "Department is required.";
        if (s.getYear() < 1 || s.getYear() > 4)  return "Year must be between 1 and 4.";
        if (s.getCgpa() < 0.0 || s.getCgpa() > 10.0) return "CGPA must be between 0.00 and 10.00.";
        return null;
    }
}
