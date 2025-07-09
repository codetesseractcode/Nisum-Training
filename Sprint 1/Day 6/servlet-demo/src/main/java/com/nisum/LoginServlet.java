package com.nisum;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.html");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if ("Ayusman".equals(username) && "password".equals(password)) {
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html>");
                out.println("<html><head><title>Login Success</title></head><body>");
                out.println("<h2>Welcome, " + username + "!</h2>");
                out.println("</body></html>");
            }
        } else {
            request.setAttribute("errorMsg", "Invalid username or password.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/login.html");
            dispatcher.forward(request, response);
        }
    }
}