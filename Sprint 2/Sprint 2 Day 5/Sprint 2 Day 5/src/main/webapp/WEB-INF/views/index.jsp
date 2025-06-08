<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modern Todo List</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
    <div class="container">
        <header>
            <h1><i class="fas fa-check-circle"></i> Todo App</h1>
            <p class="subtitle">Stay organized, be productive</p>
        </header>

        <div class="todo-container">
            <div class="filters">
                <button class="filter-btn active" data-filter="all">All</button>
                <button class="filter-btn" data-filter="pending">Pending</button>
                <button class="filter-btn" data-filter="completed">Completed</button>
            </div>

            <form id="todo-form" class="todo-form">
                <div class="form-group">
                    <input type="text" id="todo-title" placeholder="What needs to be done?" required>
                    <textarea id="todo-description" placeholder="Description (optional)"></textarea>
                    <div class="form-row">
                        <div class="form-group">
                            <label for="todo-due-date">Due Date:</label>
                            <input type="date" id="todo-due-date">
                        </div>
                        <div class="form-group">
                            <label for="todo-priority">Priority:</label>
                            <select id="todo-priority">
                                <option value="LOW">Low</option>
                                <option value="MEDIUM" selected>Medium</option>
                                <option value="HIGH">High</option>
                            </select>
                        </div>
                    </div>
                </div>
                <button type="submit" class="add-btn">Add Task</button>
            </form>

            <ul id="todo-list" class="todo-list">
                <!-- Todo items will be inserted here dynamically -->
            </ul>
        </div>
    </div>

    <!-- Todo Item Template -->
    <template id="todo-item-template">
        <li class="todo-item" data-id="">
            <div class="todo-content">
                <div class="todo-header">
                    <input type="checkbox" class="todo-checkbox">
                    <h3 class="todo-title"></h3>
                    <span class="priority-badge"></span>
                </div>
                <p class="todo-description"></p>
                <div class="todo-meta">
                    <span class="todo-date"><i class="far fa-calendar-alt"></i> Due: <span class="due-date"></span></span>
                    <span class="todo-created"><i class="far fa-clock"></i> Created: <span class="created-date"></span></span>
                </div>
            </div>
            <div class="todo-actions">
                <button class="edit-btn"><i class="fas fa-edit"></i></button>
                <button class="delete-btn"><i class="fas fa-trash"></i></button>
            </div>
        </li>
    </template>

    <!-- Edit Modal -->
    <div id="edit-modal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>Edit Task</h2>
            <form id="edit-form">
                <input type="hidden" id="edit-id">
                <div class="form-group">
                    <label for="edit-title">Title:</label>
                    <input type="text" id="edit-title" required>
                </div>
                <div class="form-group">
                    <label for="edit-description">Description:</label>
                    <textarea id="edit-description"></textarea>
                </div>
                <div class="form-row">
                    <div class="form-group">
                        <label for="edit-due-date">Due Date:</label>
                        <input type="date" id="edit-due-date">
                    </div>
                    <div class="form-group">
                        <label for="edit-priority">Priority:</label>
                        <select id="edit-priority">
                            <option value="LOW">Low</option>
                            <option value="MEDIUM">Medium</option>
                            <option value="HIGH">High</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="edit-completed">Completed:</label>
                    <input type="checkbox" id="edit-completed">
                </div>
                <button type="submit" class="save-btn">Save Changes</button>
            </form>
        </div>
    </div>

    <script src="<c:url value='/resources/js/todo.js'/>"></script>
</body>
</html>

