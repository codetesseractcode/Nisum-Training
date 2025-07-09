package com.nisum;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;

public class Page1Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<form method='post'>");
        out.println("Name: <input type='text' name='name' required>");
        out.println("<input type='submit' value='Next'>");
        out.println("</form>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        request.getSession().setAttribute("name", name);
        response.sendRedirect("page2");
    }
}