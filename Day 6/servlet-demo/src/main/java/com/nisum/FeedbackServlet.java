package com.nisum;

import java.io.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class FeedbackServlet extends HttpServlet {
    private static final List<String> feedbackList = Collections.synchronizedList(new ArrayList<>());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>User Feedback</h2>");
        out.println("<form method='post'>");
        out.println("Feedback: <input type='text' name='feedback' required>");
        out.println("<input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("<h3>All Feedback:</h3><ul>");
        synchronized (feedbackList) {
            for (String fb : feedbackList) {
                out.println("<li>" + fb + "</li>");
            }
        }
        out.println("</ul>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String feedback = request.getParameter("feedback");
        if (feedback != null && !feedback.trim().isEmpty()) {
            feedbackList.add(feedback.trim());
        }
        response.sendRedirect("feedback");
    }
}