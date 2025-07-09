package com.nisum;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class RedirectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("https://www.nisum.com/");
    }
}