package com.sms;

import java.util.ArrayList;
import java.io.*;
import java.util.List;

public class StudentService {

    private ArrayList<Students> studentList = new ArrayList<>();

    // Check duplicate ID
    private boolean isDuplicateId(int id) {
        for (Students s : studentList) {
            if (s.getId() == id) {
                return true;
            }
        }
        return false;
    }

    // Add student
    public void addStudent(Students student) {
        if (isDuplicateId(student.getId())) {
            System.out.println("Error: Student ID already exists!");
            return;
        }

        studentList.add(student);
        System.out.println("Student added successfully!");
    }

    // View all students
    public void viewStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("\n--- Student List ---");
        for (Students s : studentList) {
            s.display();
        }
    }

    // Delete student
    public void deleteStudent(int id) {
        for (int i = 0; i < studentList.size(); i++) {
            if (studentList.get(i).getId() == id) {
                studentList.remove(i);
                System.out.println("Student deleted successfully!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Search student
    public void searchStudent(int id) {
        for (Students s : studentList) {
            if (s.getId() == id) {
                System.out.println("\n--- Student Found ---");
                s.display();
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Update student
    public void updateStudent(int id, String newName, double newMarks) {
        for (Students s : studentList) {
            if (s.getId() == id) {
                s.setName(newName);
                s.setMarks(newMarks);
                System.out.println("Student updated successfully!");
                return;
            }
        }
        System.out.println("Student not found.");
    }

    // Sort by marks (descending)
    public void sortByMarks() {
        studentList.sort((s1, s2) -> Double.compare(s2.getMarks(), s1.getMarks()));
        System.out.println("Students sorted by marks (descending).");
    }

    // Sort by name (A-Z)
    public void sortByName() {
        studentList.sort((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
        System.out.println("Students sorted by name.");
    }
    
    public List<Students> getAllStudents() {
        return studentList;
    }

    // Save to file
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"))) {
            for (Students s : studentList) {
                writer.write(s.getId() + "," + s.getName() + "," + s.getMarks());
                writer.newLine();
            }
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // Load from file
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                int id = Integer.parseInt(data[0]);
                String name = data[1];
                double marks = Double.parseDouble(data[2]);

                studentList.add(new Students(id, name, marks));
            }

        } catch (IOException e) {
            System.out.println("No previous data found.");
        }
    }
}