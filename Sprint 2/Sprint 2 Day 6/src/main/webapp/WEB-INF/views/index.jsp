<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ES6 Todo Application</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
    <div class="container">
        <h1>ES6 Todo Application</h1>

        <div class="todo-form">
            <input type="text" id="todo-title" placeholder="Enter title" required>
            <textarea id="todo-description" placeholder="Enter description" rows="3"></textarea>
            <button id="add-todo">Add Todo</button>
        </div>

        <div class="filters">
            <button class="filter-btn active" data-filter="all">All</button>
            <button class="filter-btn" data-filter="active">Active</button>
            <button class="filter-btn" data-filter="completed">Completed</button>
        </div>

        <ul id="todo-list"></ul>
    </div>

    <!-- ES6 Modules -->
    <script type="module" src="${pageContext.request.contextPath}/resources/js/main.js"></script>
</body>
</html>

