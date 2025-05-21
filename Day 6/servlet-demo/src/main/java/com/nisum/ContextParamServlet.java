package com.nisum;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ContextParamServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = getServletContext();
        String basePath = context.getInitParameter("basePath");

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Context Param Servlet</title></head><body>");
            out.println("<h2>Context Parameter</h2>");
            out.println("<p>Base Path: " + basePath + "</p>");
            out.println("</body></html>");
        }
    }
}