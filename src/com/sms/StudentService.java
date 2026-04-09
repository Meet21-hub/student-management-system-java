package com.sms;
import java.util.ArrayList;
import java.io.*;

public class StudentService {

    private ArrayList<Students> studentList = new ArrayList<>();

    // Add student
    public void addStudent(Students student) {
        studentList.add(student);
        System.out.println("Student added successfully!");
    }

    // View all students
    public void viewStudents() {
        if (studentList.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        for (Students s : studentList) {
            s.display();
        }
    }

    // Delete student by ID
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

    // Search student by ID
    public void searchStudent(int id) {
        for (Students s : studentList) {
            if (s.getId() == id) {
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
    
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"))) {
            for (Students s : studentList) {
                writer.write(s.getId() + "," + s.getName() + "," + s.getMarks());
                writer.newLine();
            }
        } 
        catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }
    
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