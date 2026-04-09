package com.sms;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        StudentService service = new StudentService();
        service.loadFromFile();
        
        int choice;

        do {
            System.out.println("\n--- Student Management System ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Delete Student");
            System.out.println("4. Search Student");
            System.out.println("5. Update Student");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");

            choice = sc.nextInt();

            switch (choice) {

                case 1 -> {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // consume newline

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Marks: ");
                    double marks = sc.nextDouble();

                    Students student = new Students(id, name, marks);
                    service.addStudent(student);
                }

                case 2 -> service.viewStudents();

                case 3 -> {
                    System.out.print("Enter ID to delete: ");
                    int deleteId = sc.nextInt();
                    service.deleteStudent(deleteId);
                }

                case 4 -> {
                    System.out.print("Enter ID to search: ");
                    int searchId = sc.nextInt();
                    service.searchStudent(searchId);
                }

                case 5 -> {
                    System.out.print("Enter ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine(); // consume newline

                    System.out.print("Enter new name: ");
                    String newName = sc.nextLine();

                    System.out.print("Enter new marks: ");
                    double newMarks = sc.nextDouble();

                    service.updateStudent(updateId, newName, newMarks);
                }

                case 6 -> {
                    service.saveToFile();
                    System.out.println("Exiting...");
                }

                default -> System.out.println("Invalid choice");
            }

        } while (choice != 6);

        sc.close();
    }
}