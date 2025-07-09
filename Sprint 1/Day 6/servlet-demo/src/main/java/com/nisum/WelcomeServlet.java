package com.nisum;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

// This annotation eliminates the need for web.xml mapping
@WebServlet(name = "WelcomeServlet", urlPatterns = {"/welcome", "/home"})
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println("WelcomeServlet doGet method called"); // Debug message

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Welcome Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Welcome to Servlets!</h1>");
            out.println("<p>Servlet path: " + request.getServletPath() + "</p>");
            out.println("<p>Context path: " + request.getContextPath() + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}