<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Student List - CRUD Operations</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <div class="d-flex justify-content-between align-items-center mb-4">
            <h1>Student List</h1>
            <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">Back to Home</a>
        </div>

        <!-- Messages -->
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <!-- Create Student Button -->
        <div class="mb-3">
            <a href="${pageContext.request.contextPath}/students/new" class="btn btn-primary">Add New Student</a>
        </div>

        <!-- Student Table -->
        <div class="card">
            <div class="card-body">
                <table class="table table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Email</th>
                            <th>Course</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${empty students}">
                                <tr>
                                    <td colspan="5" class="text-center">No students found</td>
                                </tr>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${students}" var="student">
                                    <tr>
                                        <td>${student.id}</td>
                                        <td>${student.name}</td>
                                        <td>${student.email}</td>
                                        <td>${student.course}</td>
                                        <td>
                                            <div class="btn-group" role="group">
                                                <a href="${pageContext.request.contextPath}/students/view/${student.id}"
                                                   class="btn btn-info btn-sm">View</a>
                                                <a href="${pageContext.request.contextPath}/students/edit/${student.id}"
                                                   class="btn btn-warning btn-sm">Edit</a>
                                                <form action="${pageContext.request.contextPath}/students/delete/${student.id}"
                                                      method="post" style="display: inline;">
                                                    <button type="submit" class="btn btn-danger btn-sm"
                                                            onclick="return confirm('Are you sure you want to delete this student?')">
                                                        Delete
                                                    </button>
                                                </form>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
