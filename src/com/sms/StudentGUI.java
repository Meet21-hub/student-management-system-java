package com.sms;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StudentGUI {

    public static void main(String[] args) {

        // Service
        StudentService service = new StudentService();
        service.loadFromFile();

        // Frame
        JFrame frame = new JFrame("Student Management System");
        frame.setSize(550, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // 🔥 Save data on close
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                service.saveToFile();
            }
        });

        // Title
        JLabel title = new JLabel("Student Management System");
        title.setBounds(150, 10, 250, 30);
        frame.add(title);

        // Labels & Fields
        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(50, 50, 100, 30);

        JTextField idField = new JTextField();
        idField.setBounds(150, 50, 150, 30);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 90, 100, 30);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 90, 150, 30);

        JLabel marksLabel = new JLabel("Marks:");
        marksLabel.setBounds(50, 130, 100, 30);

        JTextField marksField = new JTextField();
        marksField.setBounds(150, 130, 150, 30);

        // Buttons
        JButton addButton = new JButton("Add");
        addButton.setBounds(50, 180, 100, 30);

        JButton viewButton = new JButton("View");
        viewButton.setBounds(170, 180, 100, 30);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(290, 180, 100, 30);

        JButton updateButton = new JButton("Update");
        updateButton.setBounds(50, 220, 100, 30);

        JButton deleteButton = new JButton("Delete");
        deleteButton.setBounds(170, 220, 100, 30);

        // Table
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Marks");

        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(50, 280, 430, 200);

        //  Refresh Table Function
        Runnable refreshTable = () -> {
            model.setRowCount(0);
            for (Students s : service.getAllStudents()) {
                model.addRow(new Object[]{
                        s.getId(),
                        s.getName(),
                        s.getMarks()
                });
            }
        };

        // Add components
        frame.add(idLabel);
        frame.add(idField);
        frame.add(nameLabel);
        frame.add(nameField);
        frame.add(marksLabel);
        frame.add(marksField);

        frame.add(addButton);
        frame.add(viewButton);
        frame.add(searchButton);
        frame.add(updateButton);
        frame.add(deleteButton);

        frame.add(scrollPane);

        //  ADD
        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double marks = Double.parseDouble(marksField.getText());

                Students student = new Students(id, name, marks);
                service.addStudent(student);

                JOptionPane.showMessageDialog(frame, "Student Added!");

                idField.setText("");
                nameField.setText("");
                marksField.setText("");

                refreshTable.run();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Input!");
            }
        });

        //  VIEW
        viewButton.addActionListener(e -> refreshTable.run());

        //  SEARCH
        searchButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());

                for (Students s : service.getAllStudents()) {
                    if (s.getId() == id) {
                        nameField.setText(s.getName());
                        marksField.setText(String.valueOf(s.getMarks()));
                        return;
                    }
                }

                JOptionPane.showMessageDialog(frame, "Student not found");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input");
            }
        });

        // ️ UPDATE
        updateButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                double marks = Double.parseDouble(marksField.getText());

                service.updateStudent(id, name, marks);

                JOptionPane.showMessageDialog(frame, "Updated successfully!");

                refreshTable.run();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input");
            }
        });

        //  DELETE
        deleteButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());

                int confirm = JOptionPane.showConfirmDialog(
                        frame,
                        "Are you sure?",
                        "Confirm Delete",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    service.deleteStudent(id);
                    JOptionPane.showMessageDialog(frame, "Deleted successfully!");
                    refreshTable.run();
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Enter valid ID");
            }
        });

        // Show frame
        frame.setVisible(true);
    }
}