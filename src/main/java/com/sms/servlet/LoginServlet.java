package com.sms.servlet;

import com.sms.dao.AdminDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * LoginServlet — Handles admin authentication (GET + POST).
 *
 * GET  → /login  → Displays login.jsp
 * POST → /login  → Validates credentials, creates session, redirects to dashboard
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final AdminDAO adminDAO = new AdminDAO();

    /** GET: Show the login page. Redirect to dashboard if already logged in. */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("adminUser") != null) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    /** POST: Validate credentials, create session, redirect. */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Input validation
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty()) {
            req.setAttribute("error", "Username and password are required.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        // Validate against DB
        boolean isValid = adminDAO.validateAdmin(username.trim(), password.trim());

        if (isValid) {
            // Create session — store username
            HttpSession session = req.getSession(true);
            session.setAttribute("adminUser", username.trim());
            session.setMaxInactiveInterval(30 * 60); // 30 minutes

            resp.sendRedirect(req.getContextPath() + "/dashboard");
        } else {
            req.setAttribute("error", "Invalid username or password. Please try again.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
