// Task 9: Todo List with Local Storage
// Create a todo list with add, delete, and mark complete functionality that persists in localStorage

// DOM Elements
const todoInput = document.getElementById('todoInput');
const addTodoBtn = document.getElementById('addTodoBtn');
const todoList = document.getElementById('todoList');
const emptyList = document.getElementById('emptyList');

// Array to store todo items
let todos = [];

// Load todos from localStorage on page load
document.addEventListener('DOMContentLoaded', function() {
    loadTodos();
    renderTodos();
});

// Function to load todos from localStorage
function loadTodos() {
    const storedTodos = localStorage.getItem('todos');
    if (storedTodos) {
        todos = JSON.parse(storedTodos);
    }
}

// Function to save todos to localStorage
function saveTodos() {
    localStorage.setItem('todos', JSON.stringify(todos));
}

// Function to render the todo list
function renderTodos() {
    // Clear the current list
    todoList.innerHTML = '';
    
    // Show/hide empty list message
    if (todos.length === 0) {
        emptyList.style.display = 'block';
    } else {
        emptyList.style.display = 'none';
        
        // Create list items for each todo
        todos.forEach((todo, index) => {
            // Create list item
            const li = document.createElement('li');
            li.className = `todo-item ${todo.completed ? 'completed' : ''}`;
            
            // Create text container
            const textSpan = document.createElement('span');
            textSpan.className = 'todo-text';
            textSpan.textContent = todo.text;
            li.appendChild(textSpan);
            
            // Create actions container
            const actions = document.createElement('div');
            actions.className = 'todo-actions';
            
            // Create toggle complete button
            const toggleBtn = document.createElement('button');
            toggleBtn.className = 'todo-btn complete-btn';
            toggleBtn.textContent = todo.completed ? 'Undo' : 'Complete';
            toggleBtn.addEventListener('click', () => toggleTodo(index));
            
            // Create delete button
            const deleteBtn = document.createElement('button');
            deleteBtn.className = 'todo-btn delete-btn';
            deleteBtn.textContent = 'Delete';
            deleteBtn.addEventListener('click', () => deleteTodo(index));
            
            // Add buttons to actions
            actions.appendChild(toggleBtn);
            actions.appendChild(deleteBtn);
            
            // Add actions to list item
            li.appendChild(actions);
            
            // Add list item to the list
            todoList.appendChild(li);
        });
    }
}

// Function to add a new todo
function addTodo() {
    const text = todoInput.value.trim();
    
    if (text) {
        todos.push({
            text: text,
            completed: false
        });
        
        saveTodos();
        renderTodos();
        todoInput.value = '';
    }
}

// Function to toggle todo completion status
function toggleTodo(index) {
    todos[index].completed = !todos[index].completed;
    saveTodos();
    renderTodos();
}

// Function to delete a todo
function deleteTodo(index) {
    todos.splice(index, 1);
    saveTodos();
    renderTodos();
}

// Event Listeners
addTodoBtn.addEventListener('click', addTodo);

todoInput.addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        addTodo();
    }
});
