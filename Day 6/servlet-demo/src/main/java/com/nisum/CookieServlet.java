package com.nisum;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet(name = "CookieServlet", urlPatterns = {"/cookie"})
public class CookieServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                    break;
                }
            }
        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Cookie Servlet</title></head><body>");
            if (username != null) {
                out.println("<h2>Welcome back, " + username + "!</h2>");
            } else {
                out.println("<form method='post' action='" + request.getContextPath() + "/cookie'>");
                out.println("Enter your name: <input type='text' name='username' required>");
                out.println("<input type='submit' value='Set Cookie'>");
                out.println("</form>");
            }
            out.println("</body></html>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        if (username != null && !username.isEmpty()) {
            Cookie cookie = new Cookie("username", username);
            cookie.setMaxAge(60 * 60 * 24 * 7); // 1 week
            response.addCookie(cookie);
        }
        response.sendRedirect(request.getContextPath() + "/cookie");
    }
}