package com.sms.servlet;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * DashboardServlet — Loads summary data and forwards to dashboard.jsp.
 *
 * GET → /dashboard → Fetches total count + recent students → dashboard.jsp
 *
 * Access is protected: redirects to /login if no session.
 */
@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    private final StudentDAO studentDAO = new StudentDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ── Session Guard ───────────────────────────────────────────────────
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("adminUser") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // ── Fetch Dashboard Data ────────────────────────────────────────────
        int totalStudents       = studentDAO.getTotalCount();
        List<Student> recent    = studentDAO.getRecentStudents();

        req.setAttribute("totalStudents", totalStudents);
        req.setAttribute("recentStudents", recent);

        req.getRequestDispatcher("/dashboard.jsp").forward(req, resp);
    }
}
