/**
 * Todo List Application in TypeScript
 * Implementing all required TypeScript concepts
 */

// Enum for Todo Priority - Moved from types.ts to make it available in browser context
enum TodoPriority {
    HIGH = 'HIGH',
    MEDIUM = 'MEDIUM',
    LOW = 'LOW'
}

// Basic Types from types.ts
type TodoId = number;
type TodoTitle = string;
type TodoDescription = string;
type TodoStatus = boolean;
type TodoDate = Date | string | null;
type TodoFilter = 'all' | 'completed' | 'pending';

// Interface for Todo
interface ITodo {
    id: TodoId;
    title: TodoTitle;
    description: TodoDescription;
    completed: TodoStatus;
    createdDate: TodoDate;
    dueDate: TodoDate;
    priority: TodoPriority;
}

// Optional properties with null
interface TodoCreateRequest {
    title: TodoTitle;
    description?: TodoDescription;
    dueDate?: TodoDate;
    priority?: TodoPriority;
}

// Type Guards
function isTodo(obj: any): obj is ITodo {
    return obj &&
        typeof obj.id === 'number' &&
        typeof obj.title === 'string' &&
        typeof obj.completed === 'boolean';
}

// Nullable Types
type NullableTodo = ITodo | null;

// Type Aliases with literal types
type PriorityColors = {
    [key in TodoPriority]: string;
};

// Constants
const API_BASE_URL: string = '/Sprint-2-Day-7/api/todos';

// State variables with type annotations
let currentFilter: TodoFilter = 'all';
let todos: ITodo[] = [];
let currentEditTodo: NullableTodo = null;

// Literal type for priority colors
const priorityColors: PriorityColors = {
    [TodoPriority.HIGH]: 'red',
    [TodoPriority.MEDIUM]: 'orange',
    [TodoPriority.LOW]: 'green'
};

// DOM Elements will be initialized in init()
let todoForm: HTMLFormElement | null;
let todoList: HTMLUListElement | null;
let todoTitle: HTMLInputElement | null;
let todoDescription: HTMLTextAreaElement | null;
let todoDueDate: HTMLInputElement | null;
let todoPriority: HTMLSelectElement | null;
let filterButtons: NodeListOf<HTMLButtonElement>;
let editModal: HTMLDivElement | null;
let closeModalBtn: HTMLElement | null;
let editForm: HTMLFormElement | null;

// TodoItem class with type annotations
class TodoItem implements ITodo {
    id: TodoId;
    title: TodoTitle;
    description: TodoDescription;
    completed: TodoStatus;
    createdDate: TodoDate;
    dueDate: TodoDate;
    priority: TodoPriority;

    constructor(
        id: TodoId,
        title: TodoTitle,
        description: TodoDescription,
        completed: TodoStatus = false,
        createdDate: TodoDate = new Date(),
        dueDate: TodoDate = null,
        priority: TodoPriority = TodoPriority.MEDIUM
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.createdDate = createdDate;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    // Method to toggle completed status
    toggleComplete(): boolean {
        this.completed = !this.completed;
        return this.completed;
    }

    // Method to format dates
    formatDate(date: TodoDate): string {
        if (!date) return 'Not set';
        const d = new Date(date);
        return d.toLocaleDateString();
    }

    // Getters for formatted dates
    get formattedCreatedDate(): string {
        return this.formatDate(this.createdDate);
    }

    get formattedDueDate(): string {
        return this.formatDate(this.dueDate);
    }
}

// Function to create a TodoItem instance from a plain object
function createTodoFromObject(obj: any): ITodo {
    if (!isTodo(obj)) {
        throw new Error('Invalid Todo object');
    }

    return new TodoItem(
        obj.id,
        obj.title,
        obj.description,
        obj.completed,
        obj.createdDate,
        obj.dueDate,
        obj.priority as TodoPriority
    );
}

// Generic function to handle API requests
async function apiRequest<T>(url: string, method: string = 'GET', data?: any): Promise<T> {
    const options: RequestInit = {
        method,
        headers: {
            'Content-Type': 'application/json'
        }
    };

    if (data) {
        options.body = JSON.stringify(data);
    }

    try {
        const response = await fetch(url, options);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        // For DELETE requests that return no content
        if (response.status === 204) {
            return {} as T;
        }

        return await response.json() as T;
    } catch (error) {
        console.error('API request error:', error);
        throw error;
    }
}

// Function to fetch todos from the server
async function fetchTodos(): Promise<void> {
    console.log('Fetching todos from API:', API_BASE_URL);

    try {
        const data = await apiRequest<ITodo[]>(API_BASE_URL);
        console.log('Todos received from server:', data);

        if (data && data.length > 0) {
            // Convert plain objects to TodoItem instances
            todos = data.map(todo => createTodoFromObject(todo));
            displayTodos();
        }
    } catch (error) {
        console.error('Error fetching todos:', error);
        // Demo todos already added at initialization
    }
}

// Type guard function for event targets
function isHTMLElement(target: EventTarget | null): target is HTMLElement {
    return target !== null && target instanceof HTMLElement;
}

// Event handler for todo list clicks
function handleTodoListClick(event: MouseEvent): void {
    const target = event.target;

    if (!isHTMLElement(target)) {
        return;
    }

    // Find the parent todo item
    const todoItem = target.closest('.todo-item') as HTMLElement | null;

    if (!todoItem) {
        return;
    }

    const todoId = Number(todoItem.getAttribute('data-id'));

    // Handle checkbox click for completing a todo
    if (target.classList.contains('todo-checkbox')) {
        const checkbox = target as HTMLInputElement;
        toggleTodoComplete(todoId, checkbox.checked);
    }

    // Handle edit button click
    if (target.classList.contains('edit-btn')) {
        openEditModal(todoId);
    }

    // Handle delete button click
    if (target.classList.contains('delete-btn')) {
        deleteTodo(todoId);
    }
}

// Function to toggle todo completion
async function toggleTodoComplete(id: TodoId, completed: boolean): Promise<void> {
    const todo = todos.find(todo => todo.id === id);

    if (!todo) {
        return;
    }

    todo.completed = completed;

    try {
        await apiRequest<ITodo>(`${API_BASE_URL}/${id}`, 'PUT', todo);
        displayTodos();
    } catch (error) {
        console.error('Error updating todo:', error);
        // Revert the change in case of error
        todo.completed = !completed;
        displayTodos();
    }
}

// Function to open the edit modal
function openEditModal(id: TodoId): void {
    const todo = todos.find(todo => todo.id === id);

    if (!todo || !editModal) {
        return;
    }

    currentEditTodo = todo;

    // Populate form fields
    const editTitle = document.getElementById('edit-title') as HTMLInputElement;
    const editDescription = document.getElementById('edit-description') as HTMLTextAreaElement;
    const editDueDate = document.getElementById('edit-due-date') as HTMLInputElement;
    const editPriority = document.getElementById('edit-priority') as HTMLSelectElement;
    const editCompleted = document.getElementById('edit-completed') as HTMLInputElement;

    if (editTitle) editTitle.value = todo.title;
    if (editDescription) editDescription.value = todo.description || '';
    if (editDueDate && todo.dueDate) {
        const dueDate = new Date(todo.dueDate);
        editDueDate.value = dueDate.toISOString().split('T')[0];
    }
    if (editPriority) editPriority.value = todo.priority;
    if (editCompleted) editCompleted.checked = todo.completed;

    // Show the modal
    editModal.style.display = 'block';
}

// Function to close the edit modal
function closeModal(): void {
    if (editModal) {
        editModal.style.display = 'none';
    }
    currentEditTodo = null;
}

// Function to handle edit form submission
async function handleEditFormSubmit(event: Event): Promise<void> {
    event.preventDefault();

    if (!currentEditTodo || !editForm) {
        return;
    }

    const formData = new FormData(editForm);

    const updatedTodo: ITodo = {
        ...currentEditTodo,
        title: formData.get('edit-title') as string,
        description: formData.get('edit-description') as string,
        dueDate: formData.get('edit-due-date') ? new Date(formData.get('edit-due-date') as string) : null,
        priority: formData.get('edit-priority') as TodoPriority,
        completed: formData.has('edit-completed')
    };

    try {
        await apiRequest<ITodo>(`${API_BASE_URL}/${currentEditTodo.id}`, 'PUT', updatedTodo);

        // Update the local todos array
        const index = todos.findIndex(todo => todo.id === currentEditTodo!.id);
        if (index !== -1) {
            todos[index] = createTodoFromObject(updatedTodo);
        }

        closeModal();
        displayTodos();
    } catch (error) {
        console.error('Error updating todo:', error);
    }
}

// Function to delete a todo
async function deleteTodo(id: TodoId): Promise<void> {
    if (!confirm('Are you sure you want to delete this todo?')) {
        return;
    }

    try {
        await apiRequest<void>(`${API_BASE_URL}/${id}`, 'DELETE');

        // Remove from local array
        todos = todos.filter(todo => todo.id !== id);
        displayTodos();
    } catch (error) {
        console.error('Error deleting todo:', error);
    }
}

// Function to handle form submission for creating a new todo
async function handleFormSubmit(event: Event): Promise<void> {
    event.preventDefault();

    if (!todoForm || !todoTitle || !todoPriority) {
        return;
    }

    const newTodo: TodoCreateRequest = {
        title: todoTitle.value,
        description: todoDescription ? todoDescription.value : '',
        priority: todoPriority.value as TodoPriority,
        dueDate: todoDueDate && todoDueDate.value ? new Date(todoDueDate.value) : null
    };

    try {
        const createdTodo = await apiRequest<ITodo>(API_BASE_URL, 'POST', newTodo);

        // Add to local array
        todos.push(createTodoFromObject(createdTodo));

        // Reset form
        todoForm.reset();

        // Update display
        displayTodos();
    } catch (error) {
        console.error('Error creating todo:', error);
    }
}

// Function to display todos based on current filter
async function displayTodos(): Promise<void> {
    // Clear the current list
    if (!todoList) {
        console.error('Todo list element not found!');
        return;
    }

    todoList.innerHTML = '';

    console.log('Displaying todos with filter:', currentFilter);

    let filteredTodos: ITodo[] = [];

    try {
        // Fetch the appropriate todos based on filter
        switch(currentFilter) {
            case 'completed':
                filteredTodos = await apiRequest<ITodo[]>(`${API_BASE_URL}/completed`);
                console.log('Completed todos:', filteredTodos.length);
                break;
            case 'pending':
                filteredTodos = await apiRequest<ITodo[]>(`${API_BASE_URL}/pending`);
                console.log('Pending todos:', filteredTodos.length);
                break;
            default:
                filteredTodos = await apiRequest<ITodo[]>(API_BASE_URL);
                console.log('All todos:', filteredTodos.length);
        }

        // Update the todos array with the filtered results
        if (currentFilter === 'all') {
            todos = filteredTodos.map(todo => createTodoFromObject(todo));
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
    } catch (error) {
        console.error('Error fetching filtered todos:', error);
        // Fallback to local filtering if API call fails
        fallbackDisplayTodos();
    }
}

// Fallback function for local filtering if API calls fail
function fallbackDisplayTodos(): void {
    if (!todoList) {
        return;
    }

    // Filter todos based on current filter
    let filteredTodos: ITodo[];

    switch(currentFilter) {
        case 'completed':
            filteredTodos = todos.filter(todo => todo.completed === true);
            break;
        case 'pending':
            filteredTodos = todos.filter(todo => todo.completed === false);
            break;
        default:
            filteredTodos = [...todos];
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
function createTodoElement(todo: ITodo): HTMLElement {
    try {
        // Check if template exists
        const template = document.getElementById('todo-item-template') as HTMLTemplateElement | null;
        if (!template) {
            console.error('Todo item template not found!');
            // Fallback: create element without template
            return createTodoElementFallback(todo);
        }

        const todoElement = document.importNode(template.content, true).querySelector('.todo-item') as HTMLElement;

        // Set data attribute for id
        todoElement.setAttribute('data-id', todo.id.toString());

        // Set completed class if the todo is completed
        if (todo.completed) {
            todoElement.classList.add('completed');
        }

        // Set the content
        const checkbox = todoElement.querySelector('.todo-checkbox') as HTMLInputElement;
        checkbox.checked = todo.completed;

        const titleElement = todoElement.querySelector('.todo-title') as HTMLElement;
        titleElement.textContent = todo.title;

        const descriptionElement = todoElement.querySelector('.todo-description') as HTMLElement;
        descriptionElement.textContent = todo.description || 'No description';

        const priorityBadge = todoElement.querySelector('.priority-badge') as HTMLElement;
        priorityBadge.textContent = todo.priority;
        priorityBadge.classList.add(`priority-${todo.priority.toLowerCase()}`);

        const dueDate = todoElement.querySelector('.due-date') as HTMLElement;
        const todoInstance = todo instanceof TodoItem
            ? todo
            : new TodoItem(todo.id, todo.title, todo.description, todo.completed, todo.createdDate, todo.dueDate, todo.priority);
        dueDate.textContent = todoInstance.formattedDueDate;

        const createdDate = todoElement.querySelector('.created-date') as HTMLElement;
        createdDate.textContent = todoInstance.formattedCreatedDate;

        return todoElement;
    } catch (error) {
        console.error('Error creating todo element:', error);
        return createTodoElementFallback(todo);
    }
}

// Fallback function to create a todo element without a template
function createTodoElementFallback(todo: ITodo): HTMLElement {
    const todoElement = document.createElement('li');
    todoElement.className = 'todo-item';
    todoElement.setAttribute('data-id', todo.id.toString());

    if (todo.completed) {
        todoElement.classList.add('completed');
    }

    const todoContent = `
        <div class="todo-content">
            <input type="checkbox" class="todo-checkbox" ${todo.completed ? 'checked' : ''}>
            <div class="todo-text">
                <h3 class="todo-title">${todo.title}</h3>
                <p class="todo-description">${todo.description || 'No description'}</p>
                <div class="todo-meta">
                    <span class="priority-badge priority-${todo.priority.toLowerCase()}">${todo.priority}</span>
                    <span class="due-date">${todo instanceof TodoItem ? todo.formattedDueDate : new TodoItem(todo.id, todo.title, todo.description, todo.completed, todo.createdDate, todo.dueDate, todo.priority).formattedDueDate}</span>
                    <span class="created-date">${todo instanceof TodoItem ? todo.formattedCreatedDate : new TodoItem(todo.id, todo.title, todo.description, todo.completed, todo.createdDate, todo.dueDate, todo.priority).formattedCreatedDate}</span>
                </div>
            </div>
        </div>
        <div class="todo-actions">
            <button class="edit-btn">Edit</button>
            <button class="delete-btn">Delete</button>
        </div>
    `;

    todoElement.innerHTML = todoContent;
    return todoElement;
}

// Function to add demo todos
function addDemoTodos(): void {
    console.log('Adding demo todos for testing');

    // Only add demo todos if we don't have any
    if (todos.length === 0) {
        todos = [
            new TodoItem(1, 'Complete project', 'Finish the todo app', false, new Date(), new Date(new Date().setDate(new Date().getDate() + 3)), TodoPriority.HIGH),
            new TodoItem(2, 'Review code', 'Check for bugs', false, new Date(), new Date(new Date().setDate(new Date().getDate() + 1)), TodoPriority.MEDIUM),
            new TodoItem(3, 'Submit assignment', 'Send to instructor', true, new Date(), new Date(new Date().setDate(new Date().getDate() - 1)), TodoPriority.LOW)
        ];
        displayTodos();
    }
}

// Setup all event listeners
function setupEventListeners(): void {
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

    // Filter buttons
    if (filterButtons && filterButtons.length) {
        filterButtons.forEach(button => {
            button.addEventListener('click', function(this: HTMLButtonElement) {
                const filterType = this.getAttribute('data-filter') as TodoFilter;
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

// Main initialization function
async function init(): Promise<void> {
    console.log('Initializing Todo App...');
    // Initialize DOM elements
    todoForm = document.getElementById('todo-form') as HTMLFormElement | null;
    todoList = document.getElementById('todo-list') as HTMLUListElement | null;
    todoTitle = document.getElementById('todo-title') as HTMLInputElement | null;
    todoDescription = document.getElementById('todo-description') as HTMLTextAreaElement | null;
    todoDueDate = document.getElementById('todo-due-date') as HTMLInputElement | null;
    todoPriority = document.getElementById('todo-priority') as HTMLSelectElement | null;
    filterButtons = document.querySelectorAll('.filter-btn') as NodeListOf<HTMLButtonElement>;
    editModal = document.getElementById('edit-modal') as HTMLDivElement | null;
    closeModalBtn = document.querySelector('.close') as HTMLElement | null;
    editForm = document.getElementById('edit-form') as HTMLFormElement | null;

    // Fetch and display todos from server
    await displayTodos();
    // Set up event listeners
    setupEventListeners();
}
