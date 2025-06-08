// todo-ui.js - UI Handler for Todo application
export class TodoUI {
    constructor() {
        // Using WeakMap for private data (ES6 feature #14)
        this.notificationCounter = new WeakMap();
        this.notificationCounter.set(this, 0);

        // Initialize UI elements
        this.setupUI();
    }

    setupUI() {
        // Create a notification area
        const container = document.querySelector('.container');
        const notification = document.createElement('div');
        notification.id = 'notification';
        notification.style.display = 'none';
        notification.style.padding = '10px';
        notification.style.marginBottom = '10px';
        notification.style.backgroundColor = '#f8d7da';
        notification.style.color = '#721c24';
        notification.style.borderRadius = '4px';
        notification.style.textAlign = 'center';

        // Insert at the beginning of container
        container.insertBefore(notification, container.firstChild);
    }

    // Show error message
    showError(message, duration = 3000) {
        const notification = document.getElementById('notification');
        notification.textContent = message;
        notification.style.display = 'block';

        // Increment counter
        const currentCount = this.notificationCounter.get(this);
        this.notificationCounter.set(this, currentCount + 1);

        // Hide after duration
        setTimeout(() => {
            const currentCount = this.notificationCounter.get(this);
            this.notificationCounter.set(this, currentCount - 1);

            // Only hide if no other notifications are queued
            if (this.notificationCounter.get(this) === 0) {
                notification.style.display = 'none';
            }
        }, duration);
    }

    // Clear input fields
    clearInputs(...inputs) {
        // Using rest parameters (ES6 feature #6)
        inputs.forEach(input => {
            input.value = '';
        });
    }

    // Show loading state
    showLoading() {
        document.getElementById('add-todo').textContent = 'Adding...';
        document.getElementById('add-todo').disabled = true;
    }

    // Hide loading state
    hideLoading() {
        document.getElementById('add-todo').textContent = 'Add Todo';
        document.getElementById('add-todo').disabled = false;
    }

    // Create a todo item element
    createTodoElement(todo) {
        const todoItem = document.createElement('li');
        todoItem.className = `todo-item ${todo.completed ? 'completed' : ''}`;
        todoItem.dataset.id = todo.id;

        // Using template literals (ES6 feature #3)
        todoItem.innerHTML = `
            <div class="todo-content">
                <div class="todo-title">${todo.title}</div>
                <div class="todo-description">${todo.description || 'No description'}</div>
            </div>
            <div class="todo-actions">
                <button class="toggle-btn">${todo.completed ? 'Undo' : 'Complete'}</button>
                <button class="delete-btn">Delete</button>
            </div>
        `;

        return todoItem;
    }

    // Render a list of todos
    renderTodoList(todos, filter = 'all') {
        const todoList = document.getElementById('todo-list');
        todoList.innerHTML = '';

        // Filter todos
        let filteredTodos;
        switch (filter) {
            case 'active':
                filteredTodos = todos.filter(todo => !todo.completed);
                break;
            case 'completed':
                filteredTodos = todos.filter(todo => todo.completed);
                break;
            default:
                filteredTodos = todos;
        }

        // Using spread operator with Array.of (ES6 feature #6 and #18)
        const todoArray = Array.of(...filteredTodos);

        // Create and append todo elements
        todoArray.forEach(todo => {
            const todoElement = this.createTodoElement(todo);
            todoList.appendChild(todoElement);
        });

        // Show message if no todos
        if (todoArray.length === 0) {
            todoList.innerHTML = '<li class="no-todos">No todos found</li>';
        }
    }
}

