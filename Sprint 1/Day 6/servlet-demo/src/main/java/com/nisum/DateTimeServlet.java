package com.nisum;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "DateTimeServlet", urlPatterns = {"/datetime"})
public class DateTimeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LocalDateTime now = LocalDateTime.now();
        String formatted = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Date and Time</title></head><body>");
            out.println("<h2>Current Date and Time:</h2>");
            out.println("<p>" + formatted + "</p>");
            out.println("</body></html>");
        }
    }
}