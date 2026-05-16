package com.sms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection — JDBC Utility Class (Singleton Pattern).
 *
 * Provides a single shared connection to the MySQL database.
 * ─────────────────────────────────────────────────────────
 * HOW TO CONFIGURE:
 *   1. Set DB_URL to your MySQL server (change port/host if needed)
 *   2. Set DB_USER and DB_PASSWORD to your MySQL credentials
 *   3. Ensure mysql-connector-java.jar is in WEB-INF/lib/
 */
public class DBConnection {

    // ─── Database Configuration ───────────────────────────────────────────────
    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/student_management?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER     = "root";       // Change to your MySQL username
    private static final String DB_PASSWORD = "meet123";       // Change to your MySQL password
    private static final String DRIVER      = "com.mysql.cj.jdbc.Driver";

    // Singleton connection instance
    private static Connection connection = null;

    /**
     * Returns a live Connection to the database.
     * Creates a new connection if none exists or if the existing one is closed.
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER);

            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                System.out.println("[DBConnection] ✅ MySQL connection established.");
            }
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found. " +
                "Please add mysql-connector-java.jar to WEB-INF/lib/", e);
        }
        return connection;
    }

    /**
     * Closes the database connection gracefully.
     * Call this only on application shutdown.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("[DBConnection] 🔒 MySQL connection closed.");
            } catch (SQLException e) {
                System.err.println("[DBConnection] ❌ Error closing connection: " + e.getMessage());
            }
        }
    }
}
