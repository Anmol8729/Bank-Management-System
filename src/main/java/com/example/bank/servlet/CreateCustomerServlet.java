package com.example.bank.servlet;

import com.example.bank.model.Customer;
import com.example.bank.service.BankService;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

public class CreateCustomerServlet extends HttpServlet {

    private final BankService service = new BankService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        res.setContentType("text/html");

        try {
            Customer c = new Customer();
            c.setFullName(req.getParameter("fullName"));
            c.setDob(req.getParameter("dob"));
            c.setEmail(req.getParameter("email"));
            c.setPhone(req.getParameter("phone"));
            c.setAddress(req.getParameter("address"));

            int id = service.createCustomer(c);
            res.getWriter().println("<h2>Customer Created Successfully</h2>");
            res.getWriter().println("Customer ID: " + id);

        } catch (Exception e) {
            res.getWriter().println("<h2>Error</h2>");
            res.getWriter().println(e.getMessage());
        }
    }
}
