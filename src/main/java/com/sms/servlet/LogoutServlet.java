package com.sms.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

/**
 * LogoutServlet — Destroys the current admin session and redirects to login.
 *
 * GET → /logout → Invalidates session → Redirects to /login
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate(); // Destroy the session
        }

        // Redirect to login with logout indicator
        resp.sendRedirect(req.getContextPath() + "/login?msg=logout");
    }
}
