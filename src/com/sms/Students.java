package com.sms;

public class Students {
    private final int id;
    private String name;
    private double marks;

    // Constructor
    public Students(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getMarks() { return marks; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setMarks(double marks) { this.marks = marks; }

    // Display method
    public void display() {
        System.out.printf("ID: %-5d Name: %-15s Marks: %.2f%n", id, name, marks);
    }
}