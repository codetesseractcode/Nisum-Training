package com.nisum;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.util.*;

public class FeedbacksServlet extends HttpServlet {
    private static final List<String> feedbackList = Collections.synchronizedList(new ArrayList<>());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h2>Submit Feedback</h2>");
        out.println("<form method='post' action='feedback'>");
        out.println("<textarea name='feedback' rows='4' cols='40' required></textarea><br><br>");
        out.println("<input type='submit' value='Submit'>");
        out.println("</form>");

        out.println("<h3>All Feedbacks:</h3>");
        if (feedbackList.isEmpty()) {
            out.println("<i>No feedbacks yet.</i>");
        } else {
            out.println("<ul>");
            synchronized (feedbackList) {
                for (String fb : feedbackList) {
                    out.println("<li>" + fb + "</li>");
                }
            }
            out.println("</ul>");
        }
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