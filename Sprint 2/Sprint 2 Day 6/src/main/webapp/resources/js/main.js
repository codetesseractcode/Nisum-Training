// main.js - Entry point for the Todo application
import { TodoService } from './services/todo-service.js';
import { TodoUI } from './ui/todo-ui.js';
import { TodoValidator } from './utils/todo-validator.js';
import { TodoStats } from './utils/todo-stats.js';

// Using let and const (ES6 feature #1)
const todoService = new TodoService();
let currentFilter = 'all';

// Wait for DOM to be fully loaded
document.addEventListener('DOMContentLoaded', () => {
    // Initialize the Todo application
    const todoUI = new TodoUI();
    const todoValidator = new TodoValidator();
    const todoStats = new TodoStats();

    // Load initial data
    loadTodos();

    // Set up event listeners
    setupEventListeners(todoUI, todoValidator, todoStats);

    // Logging initial stats using template literals (ES6 feature #3)
    console.log(`Todo Application initialized at ${new Date().toLocaleString()}`);
});

// Arrow function (ES6 feature #2)
const loadTodos = async () => {
    try {
        // Using Promise (ES6 feature #9)
        const todos = await todoService.getAllTodos();
        renderTodos(todos);
    } catch (error) {
        console.error(`Error loading todos: ${error.message}`);
    }
};

// Setup event listeners using arrow functions
const setupEventListeners = (todoUI, todoValidator, todoStats) => {
    // Add Todo Button
    document.getElementById('add-todo').addEventListener('click', () => {
        const titleInput = document.getElementById('todo-title');
        const descriptionInput = document.getElementById('todo-description');

        // Destructuring assignment (ES6 feature #5)
        const { value: title } = titleInput;
        const { value: description } = descriptionInput;

        if (todoValidator.isValid(title)) {
            addTodo(title, description);
            todoUI.clearInputs(titleInput, descriptionInput);
            todoStats.updateStats();
        } else {
            todoUI.showError('Title cannot be empty!');
        }
    });

    // Filter buttons
    document.querySelectorAll('.filter-btn').forEach(button => {
        button.addEventListener('click', (e) => {
            // Update active filter
            currentFilter = e.target.dataset.filter;

            // Update active filter button
            document.querySelectorAll('.filter-btn').forEach(btn =>
                btn.classList.remove('active'));
            e.target.classList.add('active');

            // Reload todos with filter
            loadTodos();
        });
    });

    // Todo list delegation for toggle and delete
    document.getElementById('todo-list').addEventListener('click', (e) => {
        if (e.target.classList.contains('toggle-btn')) {
            const todoId = Number(e.target.closest('.todo-item').dataset.id);
            toggleTodoCompletion(todoId);
        } else if (e.target.classList.contains('delete-btn')) {
            const todoId = Number(e.target.closest('.todo-item').dataset.id);
            deleteTodo(todoId);
        }
    });
};

// Add a new todo
const addTodo = async (title, description = '') => { // Default parameters (ES6 feature #4)
    try {
        // Enhanced object literals (ES6 feature #7)
        const newTodo = {
            title,
            description,
            completed: false
        };

        await todoService.createTodo(newTodo);
        loadTodos();
    } catch (error) {
        console.error(`Error adding todo: ${error.message}`);
    }
};

// Toggle todo completion status
const toggleTodoCompletion = async (id) => {
    try {
        await todoService.toggleTodoCompletion(id);
        loadTodos();
    } catch (error) {
        console.error(`Error toggling todo: ${error.message}`);
    }
};

// Delete a todo
const deleteTodo = async (id) => {
    try {
        await todoService.deleteTodo(id);
        loadTodos();
    } catch (error) {
        console.error(`Error deleting todo: ${error.message}`);
    }
};

// Render todos based on current filter
const renderTodos = (todos) => {
    const todoList = document.getElementById('todo-list');
    todoList.innerHTML = '';

    // Filter todos based on current filter
    const filteredTodos = filterTodos(todos, currentFilter);

    // Using for...of loop (ES6 feature #11)
    for (const todo of filteredTodos) {
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

        todoList.appendChild(todoItem);
    }
};

// Filter todos based on current filter
const filterTodos = (todos, filter) => {
    // Using Array.from (ES6 feature #18)
    const todosArray = Array.from(todos);

    switch (filter) {
        case 'active':
            return todosArray.filter(todo => !todo.completed);
        case 'completed':
            return todosArray.filter(todo => todo.completed);
        default:
            return todosArray;
    }
};
