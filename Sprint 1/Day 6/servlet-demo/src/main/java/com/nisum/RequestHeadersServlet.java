package com.nisum;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class RequestHeadersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Request Headers</title></head><body>");
        out.println("<h2>Request Headers</h2>");
        out.println("<table border='1'><tr><th>Header Name</th><th>Header Value</th></tr>");

        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            out.println("<tr><td>" + name + "</td><td>" + value + "</td></tr>");
        }

        out.println("</table>");
        out.println("</body></html>");
    }
}