package com.nisum;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class SetSessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("username", "Ayusman");
        session.setAttribute("role", "admin");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Session attributes set!</h2>");
        out.println("<a href='read-session'>Read Session Attributes</a>");
        out.println("</body></html>");
    }
}