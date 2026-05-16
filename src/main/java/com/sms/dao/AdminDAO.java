package com.sms.dao;

import com.sms.util.DBConnection;

import java.sql.*;

/**
 * AdminDAO — Handles admin authentication against the database.
 *
 * Uses PreparedStatements to safely query credentials.
 * NOTE: In production use BCrypt-hashed passwords — this demo uses plain text.
 */
public class AdminDAO {

    private static final String LOGIN_SQL =
        "SELECT * FROM admin_users WHERE username = ? AND password = ?";

    /**
     * Validates admin credentials.
     * @param username admin username
     * @param password admin password (plain text for this demo)
     * @return true if credentials match, false otherwise
     */
    public boolean validateAdmin(String username, String password) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(LOGIN_SQL)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Returns true if a matching row is found
            }

        } catch (SQLException e) {
            System.err.println("[AdminDAO] ❌ validateAdmin error: " + e.getMessage());
            return false;
        }
    }
}
