package com.nisum;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class LoginServletMini extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        if ("Ayusman".equals(username) && "Ayu@123".equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", username);
            response.sendRedirect("welcome-mini");
        } else {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<div style='color:red'>Invalid credentials!</div>");
            out.println("<a href='login-mini.html'>Try Again</a>");
        }
    }
}