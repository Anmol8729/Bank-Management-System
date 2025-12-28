package com.example.bank.servlet;

import com.example.bank.model.User;
import com.example.bank.service.AuthService;

import jakarta.servlet.http.*;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        System.out.println("=== LOGIN SERVLET EXECUTED ===");
        System.out.println("Request method: " + req.getMethod());
        System.out.println("Request URI: " + req.getRequestURI());

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        System.out.println("Username: " + username);
        System.out.println("Password: " + (password != null ? "****" : "null"));

        try {
            User user = authService.login(username, password);

            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());

                res.sendRedirect("bank.html");
            } else {
                res.getWriter().println("Invalid credentials");
            }

        } catch (Exception e) {
            System.err.println("Error in LoginServlet: " + e.getMessage());
            e.printStackTrace();
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}
