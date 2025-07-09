package com.nisum;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class WelcomeServletMini extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (session != null && session.getAttribute("user") != null) {
            out.println("<h2>Welcome, " + session.getAttribute("user") + "!</h2>");
            out.println("<a href='logout-mini'>Logout</a>");
        } else {
            response.sendRedirect("login-mini.html");
        }
    }
}