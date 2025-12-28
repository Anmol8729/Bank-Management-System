package com.example.bank.servlet;

import jakarta.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        // Invalidate the session to log out the user
        req.getSession().invalidate();

        // Redirect to the login or home page
        res.sendRedirect("bank.html");
    }
}
