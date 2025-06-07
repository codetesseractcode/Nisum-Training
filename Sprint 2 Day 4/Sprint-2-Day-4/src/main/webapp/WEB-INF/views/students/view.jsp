<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>Student Details</h1>

        <div class="card">
            <div class="card-body">
                <h5 class="card-title">${student.name}</h5>
                <p class="card-text">
                    <strong>ID:</strong> ${student.id}<br>
                    <strong>Email:</strong> ${student.email}<br>
                    <strong>Course:</strong> ${student.course}<br>
                    <c:if test="${not empty student.createdAt}">
                        <strong>Created At:</strong> ${student.createdAt}<br>
                    </c:if>
                    <c:if test="${not empty student.updatedAt}">
                        <strong>Last Updated:</strong> ${student.updatedAt}
                    </c:if>
                </p>
            </div>
        </div>

        <div class="mt-3">
            <a href="<c:url value='/students/edit/${student.id}'/>" class="btn btn-warning">Edit</a>
            <a href="<c:url value='/'/>" class="btn btn-secondary">Back to List</a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
