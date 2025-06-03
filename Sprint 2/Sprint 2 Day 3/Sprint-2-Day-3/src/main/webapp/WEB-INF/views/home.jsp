<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Text Editor - Home</title>
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
        h1, h2 {
            color: #333;
        }
        .btn {
            display: inline-block;
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            margin-top: 10px;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .btn-sm {
            padding: 5px 10px;
            font-size: 0.9em;
            margin-right: 5px;
        }
        ul {
            list-style-type: none;
            padding: 0;
        }
        li {
            margin-bottom: 10px;
            padding: 15px;
            background-color: #f9f9f9;
            border-radius: 4px;
            border-left: 4px solid #4CAF50;
        }
        .doc-title {
            font-weight: bold;
            margin-bottom: 5px;
            font-size: 1.1em;
        }
        .doc-meta {
            color: #666;
            font-size: 0.9em;
            margin-bottom: 10px;
        }
        .doc-actions {
            margin-top: 10px;
        }
        .no-documents {
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 4px;
            text-align: center;
            color: #666;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Document Text Editor</h1>
        <p>Welcome to the Document Text Editor application. This application demonstrates SOLID principles and the DAO pattern.</p>

        <div>
            <h2>Get Started</h2>
            <a href="<c:url value='/document/new' />" class="btn">Create New Document</a>
        </div>

        <div>
            <h2>Your Documents</h2>
            <c:choose>
                <c:when test="${empty documents}">
                    <div class="no-documents">
                        <p>You don't have any documents yet. Create your first document to get started!</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <ul>
                        <c:forEach items="${documents}" var="doc">
                            <li>
                                <div class="doc-title">${doc.title}</div>
                                <div class="doc-meta">
                                    Created: <fmt:formatDate value="${doc.createdDate}" pattern="MMM d, yyyy" />
                                    | Last modified: <fmt:formatDate value="${doc.lastModifiedDate}" pattern="MMM d, yyyy" />
                                </div>
                                <div class="doc-actions">
                                    <a href="<c:url value='/document/${doc.id}' />" class="btn btn-sm">View</a>
                                    <a href="<c:url value='/document/${doc.id}/edit' />" class="btn btn-sm">Edit</a>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>

