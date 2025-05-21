package com.nisum;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ConfigServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletConfig config = getServletConfig();
        String appName = config.getInitParameter("appName");
        String appVersion = config.getInitParameter("appVersion");

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Config Servlet</title></head><body>");
            out.println("<h2>Initialization Parameters</h2>");
            out.println("<p>App Name: " + appName + "</p>");
            out.println("<p>App Version: " + appVersion + "</p>");
            out.println("</body></html>");
        }
    }
}