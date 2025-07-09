package com.nisum;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class TargetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String message = (String) request.getAttribute("message");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>Target Servlet</h2>");
        out.println("Message: " + message);
    }
}