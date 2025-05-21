package com.nisum;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class ForwardingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set an attribute to pass data
        request.setAttribute("message", "Hello from ForwardingServlet!");
        // Forward to TargetServlet
        RequestDispatcher dispatcher = request.getRequestDispatcher("/target");
        dispatcher.forward(request, response);
    }
}