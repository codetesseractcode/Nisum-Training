package com.nisum;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class SummaryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String name = session != null ? (String) session.getAttribute("name") : null;
        String email = session != null ? (String) session.getAttribute("email") : null;

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>Summary</h2>");
        out.println("Name: " + (name != null ? name : "") + "<br>");
        out.println("Email: " + (email != null ? email : "") + "<br>");
    }
}