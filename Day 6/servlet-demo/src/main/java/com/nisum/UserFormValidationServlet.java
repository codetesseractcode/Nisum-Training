package com.nisum;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class UserFormValidationServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("username");
        String email = request.getParameter("email");
        StringBuilder errors = new StringBuilder();

        if (name == null || name.trim().isEmpty()) {
            errors.append("Name is required.<br>");
        }
        if (email == null || email.trim().isEmpty()) {
            errors.append("Email is required.<br>");
        } else if (!email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
            errors.append("Invalid email format.<br>");
        }

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>User Form Validation</h2>");
        if (errors.length() > 0) {
            out.println("<div style='color:red'>" + errors + "</div>");
            out.println("<a href='user-form.html'>Go Back</a>");
        } else {
            out.println("<div style='color:green'>Success! Data is valid.</div>");
            out.println("Name: " + name + "<br>");
            out.println("Email: " + email + "<br>");
        }
    }
}