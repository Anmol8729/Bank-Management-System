package com.example.bank.servlet;

import com.example.bank.service.BankService;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

public class WithdrawServlet extends HttpServlet {

    private final BankService service = new BankService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/html");

        try {
            int accountId = Integer.parseInt(req.getParameter("accountId"));
            BigDecimal amount = new BigDecimal(req.getParameter("amount"));

            boolean success = service.withdraw(accountId, amount);

            res.getWriter().println(success
                    ? "<h2>Withdrawal Successful</h2>"
                    : "<h2>Insufficient Balance</h2>");

        } catch (Exception e) {
            res.getWriter().println("<h2>Error</h2>");
            res.getWriter().println(e.getMessage());
        }
    }
}
