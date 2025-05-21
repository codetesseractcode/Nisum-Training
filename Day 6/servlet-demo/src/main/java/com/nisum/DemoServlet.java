package com.nisum;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "DemoServlet", urlPatterns = {"/demo"})
public class DemoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String basePath = getServletContext().getInitParameter("basePath");
        String actionUrl = request.getContextPath() + (basePath != null ? basePath : "/demo");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Demo Servlet</title></head><body>");
            out.println("<h2>Demo: doGet() and doPost()</h2>");
            out.println("<form method='post' action='" + actionUrl + "'>");
            out.println("Name: <input type='text' name='name'><br><br>");
            out.println("<input type='submit' value='Submit'>");
            out.println("</form>");
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String basePath = getServletContext().getInitParameter("basePath");
        String backUrl = request.getContextPath() + (basePath != null ? basePath : "/demo");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Demo Servlet</title></head><body>");
            out.println("<h2>Hello, " + (name != null ? name : "Guest") + "!</h2>");
            out.println("<a href='" + backUrl + "'>Back to form</a>");
            out.println("</body></html>");
        }
    }
}