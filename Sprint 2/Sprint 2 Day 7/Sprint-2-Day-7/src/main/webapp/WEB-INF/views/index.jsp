<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>TypeScript Todo App</title>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css'/>">
</head>
<body>
    <div class="container">
        <header>
            <h1>TypeScript Todo Application</h1>
            <div class="filter-buttons">
                <button class="filter-btn active" data-filter="all">All</button>
                <button class="filter-btn" data-filter="pending">Pending</button>
                <button class="filter-btn" data-filter="completed">Completed</button>
            </div>
        </header>

        <main>
            <section class="todo-form-section">
                <h2>Add New Task</h2>
                <form id="todo-form">
                    <div class="form-group">
                        <label for="todo-title">Title:</label>
                        <input type="text" id="todo-title" name="title" required>
                    </div>
                    <div class="form-group">
                        <label for="todo-description">Description:</label>
                        <textarea id="todo-description" name="description"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="todo-due-date">Due Date:</label>
                        <input type="date" id="todo-due-date" name="dueDate">
                    </div>
                    <div class="form-group">
                        <label for="todo-priority">Priority:</label>
                        <select id="todo-priority" name="priority">
                            <option value="HIGH">High</option>
                            <option value="MEDIUM" selected>Medium</option>
                            <option value="LOW">Low</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-primary">Add Task</button>
                </form>
            </section>

            <section class="todo-list-section">
                <h2>Todo List</h2>
                <ul id="todo-list" class="todo-list">
                    <!-- Todo items will be added here -->
                </ul>
            </section>
        </main>

        <!-- Todo Item Template -->
        <template id="todo-item-template">
            <li class="todo-item">
                <div class="todo-content">
                    <input type="checkbox" class="todo-checkbox">
                    <div class="todo-text">
                        <h3 class="todo-title"></h3>
                        <p class="todo-description"></p>
                        <div class="todo-meta">
                            <span class="priority-badge"></span>
                            <span class="due-date"></span>
                            <span class="created-date"></span>
                        </div>
                    </div>
                </div>
                <div class="todo-actions">
                    <button class="edit-btn">Edit</button>
                    <button class="delete-btn">Delete</button>
                </div>
            </li>
        </template>

        <!-- Edit Todo Modal -->
        <div id="edit-modal" class="modal">
            <div class="modal-content">
                <span class="close">&times;</span>
                <h2>Edit Todo</h2>
                <form id="edit-form">
                    <div class="form-group">
                        <label for="edit-title">Title:</label>
                        <input type="text" id="edit-title" name="edit-title" required>
                    </div>
                    <div class="form-group">
                        <label for="edit-description">Description:</label>
                        <textarea id="edit-description" name="edit-description"></textarea>
                    </div>
                    <div class="form-group">
                        <label for="edit-due-date">Due Date:</label>
                        <input type="date" id="edit-due-date" name="edit-due-date">
                    </div>
                    <div class="form-group">
                        <label for="edit-priority">Priority:</label>
                        <select id="edit-priority" name="edit-priority">
                            <option value="HIGH">High</option>
                            <option value="MEDIUM">Medium</option>
                            <option value="LOW">Low</option>
                        </select>
                    </div>
                    <div class="form-group checkbox-group">
                        <label for="edit-completed">Completed:</label>
                        <input type="checkbox" id="edit-completed" name="edit-completed">
                    </div>
                    <button type="submit" class="btn-primary">Save Changes</button>
                </form>
            </div>
        </div>
    </div>

    <!-- Include TypeScript directly -->
    <script src="https://cdn.jsdelivr.net/npm/typescript@5.0.4/lib/typescript.min.js"></script>
    <script type="text/typescript" src="<c:url value='/resources/js/todo.ts'/>"></script>

    <!-- Script to compile and run TypeScript -->
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const scripts = document.querySelectorAll('script[type="text/typescript"]');
            let remaining = scripts.length;
            scripts.forEach(script => {
                const xhr = new XMLHttpRequest();
                xhr.open('GET', script.src, true);
                xhr.onload = function() {
                    if (xhr.status === 200) {
                        const js = window.ts.transpile(xhr.responseText, {
                            target: window.ts.ScriptTarget.ES2015,
                            module: window.ts.ModuleKind.None
                        });
                        const s = document.createElement('script');
                        s.textContent = js;
                        document.body.appendChild(s);
                        remaining--;
                        if (remaining === 0) {
                            console.log('TypeScript compilation complete');
                            if (typeof init === 'function') {
                                init();
                            }
                        }
                    }
                };
                xhr.send();
            });
        });
    </script>
</body>
</html>
