package com.sms.dao;

import com.sms.model.Student;
import com.sms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StudentDAO — Data Access Object for Student CRUD operations.
 *
 * Implements the DAO pattern to separate database logic from business logic.
 * All SQL queries use PreparedStatements to prevent SQL injection.
 */
public class StudentDAO {

    // ─── SQL Queries ─────────────────────────────────────────────────────────
    private static final String INSERT_SQL =
        "INSERT INTO students (full_name, email, phone, department, year, cgpa) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_SQL =
        "SELECT * FROM students ORDER BY created_at DESC";

    private static final String SELECT_BY_ID_SQL =
        "SELECT * FROM students WHERE student_id = ?";

    private static final String SEARCH_SQL =
        "SELECT * FROM students WHERE student_id = ? OR full_name LIKE ?";

    private static final String UPDATE_SQL =
        "UPDATE students SET full_name=?, email=?, phone=?, department=?, year=?, cgpa=? WHERE student_id=?";

    private static final String DELETE_SQL =
        "DELETE FROM students WHERE student_id = ?";

    private static final String COUNT_SQL =
        "SELECT COUNT(*) AS total FROM students";

    private static final String RECENT_SQL =
        "SELECT * FROM students ORDER BY created_at DESC LIMIT 5";

    // ─── Add Student ─────────────────────────────────────────────────────────

    /**
     * Inserts a new student into the database.
     * @param student Student object to insert
     * @return true if successful, false otherwise
     */
    public boolean addStudent(Student student) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {

            ps.setString(1, student.getFullName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPhone());
            ps.setString(4, student.getDepartment());
            ps.setInt(5,    student.getYear());
            ps.setDouble(6, student.getCgpa());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("[StudentDAO] ❌ addStudent error: " + e.getMessage());
            return false;
        }
    }

    // ─── Get All Students ────────────────────────────────────────────────────

    /**
     * Retrieves all students from the database, ordered by creation date.
     * @return List of Student objects
     */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("[StudentDAO] ❌ getAllStudents error: " + e.getMessage());
        }
        return students;
    }

    // ─── Get Student by ID ───────────────────────────────────────────────────

    /**
     * Retrieves a single student by their ID.
     * @param id Student ID
     * @return Student object or null if not found
     */
    public Student getStudentById(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[StudentDAO] ❌ getStudentById error: " + e.getMessage());
        }
        return null;
    }

    // ─── Search Students ─────────────────────────────────────────────────────

    /**
     * Searches for students by ID or name (partial match supported).
     * @param query Search keyword (ID or name)
     * @return List of matching students
     */
    public List<Student> searchStudents(String query) {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SEARCH_SQL)) {

            // Try parsing as integer for ID search
            int id = -1;
            try { id = Integer.parseInt(query); } catch (NumberFormatException ignored) {}

            ps.setInt(1, id);
            ps.setString(2, "%" + query + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    students.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("[StudentDAO] ❌ searchStudents error: " + e.getMessage());
        }
        return students;
    }

    // ─── Update Student ──────────────────────────────────────────────────────

    /**
     * Updates an existing student record.
     * @param student Student object with updated fields (must have valid ID)
     * @return true if successful, false otherwise
     */
    public boolean updateStudent(Student student) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, student.getFullName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getPhone());
            ps.setString(4, student.getDepartment());
            ps.setInt(5,    student.getYear());
            ps.setDouble(6, student.getCgpa());
            ps.setInt(7,    student.getStudentId());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("[StudentDAO] ❌ updateStudent error: " + e.getMessage());
            return false;
        }
    }

    // ─── Delete Student ──────────────────────────────────────────────────────

    /**
     * Deletes a student from the database by ID.
     * @param id Student ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteStudent(int id) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("[StudentDAO] ❌ deleteStudent error: " + e.getMessage());
            return false;
        }
    }

    // ─── Count Students ──────────────────────────────────────────────────────

    /**
     * Returns the total number of students in the database.
     */
    public int getTotalCount() {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(COUNT_SQL);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) return rs.getInt("total");

        } catch (SQLException e) {
            System.err.println("[StudentDAO] ❌ getTotalCount error: " + e.getMessage());
        }
        return 0;
    }

    // ─── Recent Students ─────────────────────────────────────────────────────

    /**
     * Returns the 5 most recently added students.
     */
    public List<Student> getRecentStudents() {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(RECENT_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("[StudentDAO] ❌ getRecentStudents error: " + e.getMessage());
        }
        return students;
    }

    // ─── Helper: Map ResultSet row → Student object ───────────────────────────
    private Student mapRow(ResultSet rs) throws SQLException {
        Student s = new Student();
        s.setStudentId(rs.getInt("student_id"));
        s.setFullName(rs.getString("full_name"));
        s.setEmail(rs.getString("email"));
        s.setPhone(rs.getString("phone"));
        s.setDepartment(rs.getString("department"));
        s.setYear(rs.getInt("year"));
        s.setCgpa(rs.getDouble("cgpa"));
        return s;
    }
}
