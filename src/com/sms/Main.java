package com.sms;

import java.util.Scanner;

public class Main {

    public static void showMenu() {
        System.out.println("\n--- Student Management System ---");
        System.out.println("1. Add Student");
        System.out.println("2. View Students");
        System.out.println("3. Delete Student");
        System.out.println("4. Search Student");
        System.out.println("5. Update Student");
        System.out.println("6. Sort by Marks");
        System.out.println("7. Sort by Name");
        System.out.println("8. Exit");
        System.out.print("Enter choice: ");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentService service = new StudentService();
        service.loadFromFile();

        int choice;

        do {
            showMenu();

            try {
                choice = sc.nextInt();

                switch (choice) {

                    case 1 -> {
                        try {
                            System.out.print("Enter ID: ");
                            int id = sc.nextInt();
                            sc.nextLine();

                            System.out.print("Enter Name: ");
                            String name = sc.nextLine();

                            System.out.print("Enter Marks: ");
                            double marks = sc.nextDouble();

                            Students student = new Students(id, name, marks);
                            service.addStudent(student);

                        } catch (Exception e) {
                            System.out.println("Invalid input! Please try again.");
                            sc.nextLine();
                        }
                    }

                    case 2 -> service.viewStudents();

                    case 3 -> {
                        try {
                            System.out.print("Enter ID to delete: ");
                            int deleteId = sc.nextInt();
                            service.deleteStudent(deleteId);
                        } catch (Exception e) {
                            System.out.println("Invalid input!");
                            sc.nextLine();
                        }
                    }

                    case 4 -> {
                        try {
                            System.out.print("Enter ID to search: ");
                            int searchId = sc.nextInt();
                            service.searchStudent(searchId);
                        } catch (Exception e) {
                            System.out.println("Invalid input!");
                            sc.nextLine();
                        }
                    }

                    case 5 -> {
                        try {
                            System.out.print("Enter ID to update: ");
                            int updateId = sc.nextInt();
                            sc.nextLine();

                            System.out.print("Enter new name: ");
                            String newName = sc.nextLine();

                            System.out.print("Enter new marks: ");
                            double newMarks = sc.nextDouble();

                            service.updateStudent(updateId, newName, newMarks);

                        } catch (Exception e) {
                            System.out.println("Invalid input!");
                            sc.nextLine();
                        }
                    }

                    case 6 -> service.sortByMarks();

                    case 7 -> service.sortByName();

                    case 8 -> {
                        service.saveToFile();
                        System.out.println("Exiting...");
                    }

                    default -> System.out.println("Invalid choice");
                }

            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine();
                choice = 0;
            }

        } while (choice != 8);

        sc.close();
    }
}