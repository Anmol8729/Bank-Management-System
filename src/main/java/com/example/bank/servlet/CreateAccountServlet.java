package com.example.bank.servlet;

import com.example.bank.service.BankService;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

public class CreateAccountServlet extends HttpServlet {

    private final BankService service = new BankService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/html");

        try {
            int customerId = Integer.parseInt(req.getParameter("customerId"));
            String type = req.getParameter("accountType");
            BigDecimal amount = new BigDecimal(req.getParameter("amount"));

            int accId = service.createAccount(customerId, type, amount);

            res.getWriter().println("<h2>Account Created Successfully</h2>");
            res.getWriter().println("Account ID: " + accId);

        } catch (Exception e) {
            res.getWriter().println("<h2>Error</h2>");
            res.getWriter().println(e.getMessage());
        }
    }
}
