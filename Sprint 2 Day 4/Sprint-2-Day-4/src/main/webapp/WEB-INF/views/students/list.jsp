<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Management System - All Students</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            color: #333;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
        }
        h1 {
            color: #2c3e50;
            border-bottom: 2px solid #3498db;
            padding-bottom: 10px;
        }
        .btn {
            display: inline-block;
            padding: 8px 16px;
            margin: 5px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
        }
        .btn-danger {
            background-color: #e74c3c;
        }
        .btn-success {
            background-color: #2ecc71;
        }
        .btn-warning {
            background-color: #f39c12;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            color: #333;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .message {
            padding: 10px;
            margin: 10px 0;
            border-radius: 4px;
        }
        .success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        .actions {
            white-space: nowrap;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Student Management System</h1>

        <!-- Success or Error Messages -->
        <c:if test="${not empty message}">
            <div class="message success">
                ${message}
            </div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="message error">
                ${error}
            </div>
        </c:if>

        <a href="${pageContext.request.contextPath}/students/new" class="btn btn-success">Add New Student</a>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Course</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="student" items="${students}">
                    <tr>
                        <td>${student.id}</td>
                        <td>${student.name}</td>
                        <td>${student.email}</td>
                        <td>${student.course}</td>
                        <td class="actions">
                            <a href="${pageContext.request.contextPath}/students/view/${student.id}" class="btn">View</a>
                            <a href="${pageContext.request.contextPath}/students/edit/${student.id}" class="btn btn-warning">Edit</a>
                            <a href="${pageContext.request.contextPath}/students/delete/${student.id}"
                               class="btn btn-danger"
                               onclick="return confirm('Are you sure you want to delete this student?');">Delete</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>

