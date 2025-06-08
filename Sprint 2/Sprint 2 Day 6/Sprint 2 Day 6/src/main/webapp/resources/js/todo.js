/**
 * Todo List Application
 * Demonstrating JS concepts:
 * 1. Data Types
 * 2. Functions
 * 3. Control Flow Statements
 * 4. Hoisting
 * 5. Object
 * 6. Array
 */

// Global variables
const API_BASE_URL = '/Sprint-2-Day-5/api/todos'; // Corrected API path
let currentFilter = 'all';
let todos = [];

// DOM Elements
const todoForm = document.getElementById('todo-form');
const todoList = document.getElementById('todo-list');
const todoTitle = document.getElementById('todo-title');
const todoDescription = document.getElementById('todo-description');
const todoDueDate = document.getElementById('todo-due-date');
const todoPriority = document.getElementById('todo-priority');
const filterButtons = document.querySelectorAll('.filter-btn');
const editModal = document.getElementById('edit-modal');
const closeModalBtn = document.querySelector('.close');
const editForm = document.getElementById('edit-form');

// Class definition for Todo
class TodoItem {
    constructor(id, title, description, completed, createdDate, dueDate, priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
        this.priority = priority || 'MEDIUM';
    }

    toggleComplete() {
        this.completed = !this.completed;
        return this.completed;
    }

    formatDate(date) {
        if (!date) return 'Not set';
        const d = new Date(date);
        return d.toLocaleDateString();
    }

    get formattedCreatedDate() {
        return this.formatDate(this.createdDate);
    }

    get formattedDueDate() {
        return this.formatDate(this.dueDate);
    }
}

// Main initialization function (Hoisting example)
function init() {
    console.log('Initializing Todo App...');

    // Add demo todos for testing
    addDemoTodos();

    // Try to fetch from server if possible
    fetchTodos();

    // Set up event listeners
    setupEventListeners();
}

// Setup all event listeners
function setupEventListeners() {
    // Form submissions
    if (todoForm) {
        todoForm.addEventListener('submit', handleFormSubmit);
    }

    if (editForm) {
        editForm.addEventListener('submit', handleEditFormSubmit);
    }

    // Close modal
    if (closeModalBtn) {
        closeModalBtn.addEventListener('click', closeModal);
    }

    // Todo list interactions
    if (todoList) {
        todoList.addEventListener('click', handleTodoListClick);
    }

    // Filter buttons - IMPORTANT for filtering functionality
    console.log('Setting up filter buttons:', filterButtons ? filterButtons.length : 0);
    if (filterButtons && filterButtons.length) {
        filterButtons.forEach(button => {
            button.addEventListener('click', function() {
                const filterType = this.getAttribute('data-filter');
                console.log('Filter clicked:', filterType);

                // Update active class
                filterButtons.forEach(btn => btn.classList.remove('active'));
                this.classList.add('active');

                // Update current filter and refresh display
                currentFilter = filterType;
                displayTodos();
            });
        });
    }
}

// Function to add demo todos
function addDemoTodos() {
    console.log('Adding demo todos for testing');

    // Only add demo todos if we don't have any
    if (todos.length === 0) {
        todos = [
            new TodoItem(1, 'Complete project', 'Finish the todo app', false, new Date(), new Date(new Date().setDate(new Date().getDate() + 3)), 'HIGH'),
            new TodoItem(2, 'Review code', 'Check for bugs', false, new Date(), new Date(new Date().setDate(new Date().getDate() + 1)), 'MEDIUM'),
            new TodoItem(3, 'Submit assignment', 'Send to instructor', true, new Date(), new Date(new Date().setDate(new Date().getDate() - 1)), 'LOW')
        ];
        displayTodos();
    }
}

// Function to fetch todos from the server
function fetchTodos() {
    console.log('Fetching todos from API:', API_BASE_URL);

    fetch(API_BASE_URL)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('Todos received from server:', data);

            if (data && data.length > 0) {
                // Convert plain objects to TodoItem instances
                todos = data.map(todo => new TodoItem(
                    todo.id,
                    todo.title,
                    todo.description,
                    todo.completed,
                    todo.createdDate,
                    todo.dueDate,
                    todo.priority
                ));
                displayTodos();
            }
        })
        .catch(error => {
            console.error('Error fetching todos:', error);
            // Demo todos already added at initialization
        });
}

// Function to display todos based on current filter
function displayTodos() {
    // Clear the current list
    if (!todoList) {
        console.error('Todo list element not found!');
        return;
    }

    todoList.innerHTML = '';

    console.log('Displaying todos with filter:', currentFilter);
    console.log('Total todos:', todos.length);

    // Filter todos based on current filter
    let filteredTodos;

    // Control flow with switch statement
    switch(currentFilter) {
        case 'completed':
            filteredTodos = todos.filter(todo => todo.completed === true);
            console.log('Completed todos:', filteredTodos.length);
            break;
        case 'pending':
            filteredTodos = todos.filter(todo => todo.completed === false);
            console.log('Pending todos:', filteredTodos.length);
            break;
        default:
            filteredTodos = [...todos];
            console.log('All todos:', filteredTodos.length);
    }

    // If no todos match the filter, show a message
    if (filteredTodos.length === 0) {
        const emptyMessage = document.createElement('li');
        emptyMessage.className = 'todo-item empty-message';
        emptyMessage.textContent = 'No tasks found';
        todoList.appendChild(emptyMessage);
        return;
    }

    // Create and append todo items to the list
    filteredTodos.forEach(todo => {
        const todoElement = createTodoElement(todo);
        todoList.appendChild(todoElement);
    });
}

// Function to create a todo element from template
function createTodoElement(todo) {
    try {
        // Check if template exists
        const template = document.getElementById('todo-item-template');
        if (!template) {
            console.error('Todo item template not found!');
            // Fallback: create element without template
            return createTodoElementFallback(todo);
        }

        const todoElement = document.importNode(template.content, true).querySelector('.todo-item');

        // Set data attribute for id
        todoElement.setAttribute('data-id', todo.id);

        // Set completed class if the todo is completed
        if (todo.completed) {
            todoElement.classList.add('completed');
        }

        // Set the content
        const checkbox = todoElement.querySelector('.todo-checkbox');
        checkbox.checked = todo.completed;

        const titleElement = todoElement.querySelector('.todo-title');
        titleElement.textContent = todo.title;

        const descriptionElement = todoElement.querySelector('.todo-description');
        descriptionElement.textContent = todo.description || 'No description';

        const priorityBadge = todoElement.querySelector('.priority-badge');
        priorityBadge.textContent = todo.priority;
        priorityBadge.classList.add(`priority-${todo.priority}`);

        const dueDate = todoElement.querySelector('.due-date');
        dueDate.textContent = todo.formattedDueDate;

        const createdDate = todoElement.querySelector('.created-date');
        createdDate.textContent = todo.formattedCreatedDate;

        return todoElement;
    } catch (error) {
        console.error('Error creating todo element:', error);
        return createTodoElementFallback(todo);
    }
}

// Fallback function to create todo element without template
function createTodoElementFallback(todo) {
    const todoElement = document.createElement('li');
    todoElement.className = 'todo-item';
    if (todo.completed) {
        todoElement.classList.add('completed');
    }
    todoElement.setAttribute('data-id', todo.id);

    // Simple structure
    todoElement.innerHTML = `
        <div class="todo-content">
            <div class="todo-header">
                <input type="checkbox" class="todo-checkbox" ${todo.completed ? 'checked' : ''}>
                <h3 class="todo-title">${todo.title}</h3>
                <span class="priority-badge priority-${todo.priority}">${todo.priority}</span>
            </div>
            <p class="todo-description">${todo.description || 'No description'}</p>
            <div class="todo-meta">
                <span class="todo-date"><i class="far fa-calendar-alt"></i> Due: ${todo.formattedDueDate}</span>
                <span class="todo-created"><i class="far fa-clock"></i> Created: ${todo.formattedCreatedDate}</span>
            </div>
        </div>
        <div class="todo-actions">
            <button class="edit-btn"><i class="fas fa-edit"></i></button>
            <button class="delete-btn"><i class="fas fa-trash"></i></button>
        </div>
    `;

    return todoElement;
}

// Function to handle form submission - Function expression
const handleFormSubmit = function(event) {
    event.preventDefault();

    // Get values from form
    const title = todoTitle.value.trim();
    const description = todoDescription.value.trim();
    const dueDate = todoDueDate.value;
    const priority = todoPriority.value;

    // Validate title
    if (!title) {
        alert('Please enter a title for the task');
        return;
    }

    // Create new todo object
    const newTodo = {
        title: title,
        description: description,
        completed: false,
        createdDate: new Date().toISOString(),
        dueDate: dueDate ? new Date(dueDate).toISOString() : null,
        priority: priority
    };

    // Send to server
    fetch(API_BASE_URL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(newTodo)
    })
    .then(response => response.json())
    .then(todo => {
        // Add to local array and update display
        todos.push(new TodoItem(
            todo.id,
            todo.title,
            todo.description,
            todo.completed,
            todo.createdDate,
            todo.dueDate,
            todo.priority
        ));
        displayTodos();

        // Reset form
        todoForm.reset();
    })
    .catch(error => console.error('Error creating todo:', error));
};

// Function to handle todo list click events
function handleTodoListClick(event) {
    const todoItem = event.target.closest('.todo-item');
    if (!todoItem) return;

    const todoId = Number(todoItem.getAttribute('data-id'));
    const todo = todos.find(t => t.id === todoId);

    if (!todo) return;

    // Checkbox click - toggle completion
    if (event.target.classList.contains('todo-checkbox')) {
        toggleTodoComplete(todo, todoItem);
    }

    // Edit button click
    if (event.target.classList.contains('edit-btn') ||
        event.target.closest('.edit-btn')) {
        openEditModal(todo);
    }

    // Delete button click
    if (event.target.classList.contains('delete-btn') ||
        event.target.closest('.delete-btn')) {
        deleteTodo(todoId);
    }
}

// Function to toggle todo completion status
function toggleTodoComplete(todo, todoElement) {
    // Toggle completion locally
    const completed = todo.toggleComplete();

    // Update class
    if (completed) {
        todoElement.classList.add('completed');
    } else {
        todoElement.classList.remove('completed');
    }

    // Update on server
    fetch(`${API_BASE_URL}/${todo.id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(todo)
    })
    .catch(error => console.error('Error updating todo:', error));
}

// Function to open the edit modal
function openEditModal(todo) {
    // Fill form with todo data
    document.getElementById('edit-id').value = todo.id;
    document.getElementById('edit-title').value = todo.title;
    document.getElementById('edit-description').value = todo.description || '';
    document.getElementById('edit-due-date').value = todo.dueDate ? new Date(todo.dueDate).toISOString().split('T')[0] : '';
    document.getElementById('edit-priority').value = todo.priority;
    document.getElementById('edit-completed').checked = todo.completed;

    // Display modal
    editModal.style.display = 'block';
}

// Function to close the edit modal
function closeModal() {
    editModal.style.display = 'none';
}

// Function to handle edit form submission
function handleEditFormSubmit(event) {
    event.preventDefault();

    // Get values from form
    const id = Number(document.getElementById('edit-id').value);
    const title = document.getElementById('edit-title').value.trim();
    const description = document.getElementById('edit-description').value.trim();
    const dueDate = document.getElementById('edit-due-date').value;
    const priority = document.getElementById('edit-priority').value;
    const completed = document.getElementById('edit-completed').checked;

    // Find the todo
    const todo = todos.find(t => t.id === id);
    if (!todo) return;

    // Update properties
    todo.title = title;
    todo.description = description;
    todo.dueDate = dueDate ? new Date(dueDate).toISOString() : null;
    todo.priority = priority;
    todo.completed = completed;

    // Update on server
    fetch(`${API_BASE_URL}/${id}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(todo)
    })
    .then(() => {
        // Update UI
        displayTodos();
        closeModal();
    })
    .catch(error => console.error('Error updating todo:', error));
}

// Function to delete a todo
function deleteTodo(id) {
    // Confirm deletion
    if (!confirm('Are you sure you want to delete this task?')) {
        return;
    }

    // Send delete request to server
    fetch(`${API_BASE_URL}/${id}`, {
        method: 'DELETE'
    })
    .then(() => {
        // Remove from local array
        todos = todos.filter(todo => todo.id !== id);
        displayTodos();
    })
    .catch(error => console.error('Error deleting todo:', error));
}

// Call init function when DOM is fully loaded
document.addEventListener('DOMContentLoaded', init);
