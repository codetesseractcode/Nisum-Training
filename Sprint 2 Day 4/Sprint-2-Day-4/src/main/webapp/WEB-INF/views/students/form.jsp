<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${student.id == null ? 'Create' : 'Edit'} Student</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container mt-5">
        <h1>${student.id == null ? 'Create' : 'Edit'} Student</h1>

        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}${student.id == null ? '/students' : '/students/update'}" method="post" modelAttribute="student">
            <form:hidden path="id"/>

            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <form:input path="name" class="form-control"/>
                <form:errors path="name" cssClass="text-danger"/>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <form:input path="email" type="email" class="form-control"/>
                <form:errors path="email" cssClass="text-danger"/>
            </div>

            <div class="mb-3">
                <label for="course" class="form-label">Course</label>
                <form:input path="course" class="form-control"/>
                <form:errors path="course" cssClass="text-danger"/>
            </div>

            <button type="submit" class="btn btn-primary">Save</button>
            <a href="<c:url value='/'/>" class="btn btn-secondary">Cancel</a>
        </form:form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
