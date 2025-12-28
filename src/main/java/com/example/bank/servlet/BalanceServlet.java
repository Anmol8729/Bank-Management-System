package com.example.bank.servlet;

import com.example.bank.model.Account;
import com.example.bank.service.BankService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class BalanceServlet extends HttpServlet {

    private final BankService service = new BankService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/html");

        try {
            int accountId = Integer.parseInt(req.getParameter("accountId"));
            Account acc = service.getAccount(accountId);

            if (acc != null) {
                res.getWriter().println("<h2>Balance Enquiry</h2>");
                res.getWriter().println("<p><b>Account ID:</b> " + acc.getAccountId() + "</p>");
                res.getWriter().println("<p><b>Account Type:</b> " + acc.getAccountType() + "</p>");
                res.getWriter().println("<p><b>Current Balance:</b> ₹" + acc.getBalance() + "</p>");
                res.getWriter().println("<br><a href='bank.html'>Go Back</a>");
            } else {
                res.getWriter().println("<h2>Account Not Found</h2>");
                res.getWriter().println("<a href='bank.html'>Go Back</a>");
            }

        } catch (Exception e) {
            res.getWriter().println("<h2>Error</h2>");
            res.getWriter().println(e.getMessage());
            res.getWriter().println("<br><a href='bank.html'>Go Back</a>");
        }
    }
}
