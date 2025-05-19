package org.example;

import java.sql.*;

public class Connection_Check {
    public static void main(String[] args) {
        String jdbcURL = "jdbc:mysql://localhost:3306/jdbc";
        String dbUser = "root";
        String dbPassword = "PiXeL2.O";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            System.out.println("Connected to database successfully!");
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
