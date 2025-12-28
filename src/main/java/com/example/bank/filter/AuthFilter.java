package com.example.bank.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String uri = request.getRequestURI();

        // Allow public resources
        if (uri.endsWith("bank.html")
                || uri.endsWith("index.html")
                || uri.endsWith("/login")
                || uri.contains("/css/")
                || uri.contains("/js/")
                || uri.contains("/images/")
                || uri.contains(".woff")
                || uri.contains(".ttf")) {

            chain.doFilter(req, res);
            return;
        }

        HttpSession session = request.getSession(false);
        boolean loggedIn = (session != null && session.getAttribute("user") != null);

        if (!loggedIn) {
            response.sendRedirect("bank.html");
            return;
        }

        chain.doFilter(req, res);
    }
}
