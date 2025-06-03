<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${document != null ? 'Edit Document' : 'Create New Document'}</title>
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
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        textarea {
            height: 300px;
            resize: vertical;
        }
        .btn {
            display: inline-block;
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border: none;
            border-radius: 4px;
            cursor: pointer;
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
    </style>
</head>
<body>
    <div class="container">
        <h1>${document != null ? 'Edit Document' : 'Create New Document'}</h1>

        <form action="<c:url value='${document != null ? "/document/".concat(document.id).concat("/update") : "/document/create"}'/>" method="post">
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" value="${document != null ? document.title : ''}" required>
            </div>

            <div class="form-group">
                <label for="content">Content:</label>
                <textarea id="content" name="content" required>${document != null ? document.content : ''}</textarea>
            </div>

            <div class="form-group">
                <button type="submit" class="btn">${document != null ? 'Update' : 'Create'} Document</button>
                <a href="<c:url value='/'/>" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
</body>
</html>

