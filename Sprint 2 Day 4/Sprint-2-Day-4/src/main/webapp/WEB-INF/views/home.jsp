<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student Management System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Student Management System</h1>
    <div class="list-group">
        <!-- Using absolute path without context path for more reliable mapping -->
        <a href="<%= request.getContextPath() %>/students" class="list-group-item list-group-item-action">
            View All Students (CRUD Operations)
        </a>

        <!-- GET "/students/new" → Displays the form to create a new student -->
        <a href="<%= request.getContextPath() %>/students/new" class="list-group-item list-group-item-action">
            Create New Student
        </a>

    </div>
</div>
</body>
</html>