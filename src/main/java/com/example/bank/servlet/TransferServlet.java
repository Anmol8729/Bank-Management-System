package com.example.bank.servlet;

import com.example.bank.service.BankService;

import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

public class TransferServlet extends HttpServlet {

    private final BankService service = new BankService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/html");

        try {
            int fromAcc = Integer.parseInt(req.getParameter("fromAcc"));
            int toAcc = Integer.parseInt(req.getParameter("toAcc"));
            BigDecimal amount = new BigDecimal(req.getParameter("amount"));

            boolean success = service.transfer(fromAcc, toAcc, amount);

            res.getWriter().println(success
                    ? "<h2>Transfer Successful</h2>"
                    : "<h2>Transfer Failed</h2>");

        } catch (Exception e) {
            res.getWriter().println("<h2>Error</h2>");
            res.getWriter().println(e.getMessage());
        }
    }
}
