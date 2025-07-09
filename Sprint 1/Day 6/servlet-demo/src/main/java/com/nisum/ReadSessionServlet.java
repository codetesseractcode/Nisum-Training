package com.nisum;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ReadSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        if (session != null) {
            String username = (String) session.getAttribute("username");
            String role = (String) session.getAttribute("role");
            out.println("<h2>Session Attributes</h2>");
            out.println("Username: " + username + "<br>");
            out.println("Role: " + role + "<br>");
        } else {
            out.println("<h2>No session found.</h2>");
        }
        out.println("</body></html>");
    }
}