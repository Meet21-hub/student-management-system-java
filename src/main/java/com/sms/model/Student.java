package com.sms.model;

/**
 * Student Model (POJO) — represents a student record in the database.
 * Maps to the `students` table in MySQL.
 */
public class Student {

    private int    studentId;
    private String fullName;
    private String email;
    private String phone;
    private String department;
    private int    year;
    private double cgpa;

    // ─── Constructors ────────────────────────────────────────────────────────

    /** Default constructor (required for frameworks & reflection). */
    public Student() {}

    /** Full constructor for creating a student with all fields. */
    public Student(int studentId, String fullName, String email,
                   String phone, String department, int year, double cgpa) {
        this.studentId  = studentId;
        this.fullName   = fullName;
        this.email      = email;
        this.phone      = phone;
        this.department = department;
        this.year       = year;
        this.cgpa       = cgpa;
    }

    /** Constructor without ID (used when inserting a new student). */
    public Student(String fullName, String email, String phone,
                   String department, int year, double cgpa) {
        this.fullName   = fullName;
        this.email      = email;
        this.phone      = phone;
        this.department = department;
        this.year       = year;
        this.cgpa       = cgpa;
    }

    // ─── Getters & Setters ───────────────────────────────────────────────────

    public int    getStudentId()              { return studentId; }
    public void   setStudentId(int studentId) { this.studentId = studentId; }

    public String getFullName()               { return fullName; }
    public void   setFullName(String fullName){ this.fullName = fullName; }

    public String getEmail()                  { return email; }
    public void   setEmail(String email)      { this.email = email; }

    public String getPhone()                  { return phone; }
    public void   setPhone(String phone)      { this.phone = phone; }

    public String getDepartment()               { return department; }
    public void   setDepartment(String dept)    { this.department = dept; }

    public int    getYear()                     { return year; }
    public void   setYear(int year)             { this.year = year; }

    public double getCgpa()                     { return cgpa; }
    public void   setCgpa(double cgpa)          { this.cgpa = cgpa; }

    @Override
    public String toString() {
        return "Student{id=" + studentId +
               ", name='" + fullName + '\'' +
               ", email='" + email + '\'' +
               ", dept='" + department + '\'' +
               ", year=" + year +
               ", cgpa=" + cgpa + '}';
    }
}
