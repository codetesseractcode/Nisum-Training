<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>View Document - ${document.title}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            margin-bottom: 5px;
        }
        .meta {
            color: #666;
            font-size: 0.9em;
            margin-bottom: 20px;
        }
        .content {
            white-space: pre-wrap;
            line-height: 1.5;
            border: 1px solid #ddd;
            padding: 15px;
            border-radius: 4px;
            background-color: #f9f9f9;
        }
        .message {
            background-color: #d4edda;
            color: #155724;
            padding: 10px 15px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .btn {
            display: inline-block;
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-right: 10px;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .btn-secondary {
            background-color: #6c757d;
        }
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        .actions {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <c:if test="${not empty message}">
            <div class="message">${message}</div>
        </c:if>

        <h1>${document.title}</h1>
        <div class="meta">
            <div>Created: <fmt:formatDate value="${document.createdDate}" pattern="MMM d, yyyy h:mm a" /></div>
            <div>Last Modified: <fmt:formatDate value="${document.lastModifiedDate}" pattern="MMM d, yyyy h:mm a" /></div>
        </div>

        <div class="content">${document.content}</div>

        <div class="actions">
            <a href="<c:url value='/document/${document.id}/edit' />" class="btn">Edit Document</a>
            <a href="<c:url value='/' />" class="btn btn-secondary">Back to Home</a>
        </div>
    </div>
</body>
</html>

