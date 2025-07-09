package com.nisum;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class StudentListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Update these as per your DB setup
        String jdbcURL = "jdbc:mysql://localhost:3306/jdbc";
        String dbUser = "root";
        String dbPass = "PiXeL2.O";

        out.println("<h2>Student List</h2>");
        out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Email</th></tr>");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(jdbcURL, dbUser, dbPass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, name, age FROM student");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("id") + "</td><td>"
                        + rs.getString("name") + "</td><td>"
                        + rs.getInt("age") + "</td></tr>");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.println("<tr><td colspan='3'>Error: " + e.getMessage() + "</td></tr>");
        }
        out.println("</table>");
    }
}