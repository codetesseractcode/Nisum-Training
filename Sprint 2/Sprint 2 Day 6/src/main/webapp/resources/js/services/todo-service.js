// todo-service.js - Service for handling Todo API operations
// Using ES6 Classes (ES6 feature #8)
export class TodoService {
    constructor() {
        this.baseUrl = '/api/todos';
        // Using Map (ES6 feature #13)
        this.cache = new Map();
        // Using Symbol for private property keys (ES6 feature #15)
        this.refreshSymbol = Symbol('refresh');
    }

    // Get all todos
    async getAllTodos() {
        try {
            // Using Fetch API with Promises (ES6 feature #9)
            const response = await fetch(this.baseUrl);
            if (!response.ok) throw new Error('Failed to fetch todos');

            const todos = await response.json();

            // Using Map to cache results (ES6 feature #13)
            this.cache.set('todos', todos);

            return todos;
        } catch (error) {
            console.error('Error fetching todos:', error);
            // Return cached todos if available
            return this.cache.get('todos') || [];
        }
    }

    // Get a todo by ID
    async getTodoById(id) {
        try {
            const response = await fetch(`${this.baseUrl}/${id}`);
            if (!response.ok) throw new Error('Failed to fetch todo');
            return await response.json();
        } catch (error) {
            console.error(`Error fetching todo ${id}:`, error);
            return null;
        }
    }

    // Create a new todo
    async createTodo(todo) {
        try {
            const response = await fetch(this.baseUrl, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(todo)
            });

            if (!response.ok) throw new Error('Failed to create todo');

            // Clear cache after creating
            this.cache.delete('todos');

            return await response.json();
        } catch (error) {
            console.error('Error creating todo:', error);
            throw error;
        }
    }

    // Update a todo
    async updateTodo(todo) {
        try {
            const response = await fetch(`${this.baseUrl}/${todo.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(todo)
            });

            if (!response.ok) throw new Error('Failed to update todo');

            // Clear cache after updating
            this.cache.delete('todos');

            return true;
        } catch (error) {
            console.error(`Error updating todo ${todo.id}:`, error);
            throw error;
        }
    }

    // Delete a todo
    async deleteTodo(id) {
        try {
            const response = await fetch(`${this.baseUrl}/${id}`, {
                method: 'DELETE'
            });

            if (!response.ok) throw new Error('Failed to delete todo');

            // Clear cache after deleting
            this.cache.delete('todos');

            return true;
        } catch (error) {
            console.error(`Error deleting todo ${id}:`, error);
            throw error;
        }
    }

    // Toggle todo completion status
    async toggleTodoCompletion(id) {
        try {
            const response = await fetch(`${this.baseUrl}/${id}/toggle`, {
                method: 'PATCH'
            });

            if (!response.ok) throw new Error('Failed to toggle todo');

            // Clear cache after toggling
            this.cache.delete('todos');

            return true;
        } catch (error) {
            console.error(`Error toggling todo ${id}:`, error);
            throw error;
        }
    }

    // Generator method to iterate through todos (ES6 feature #12)
    *todoIterator(todos) {
        for (let i = 0; i < todos.length; i++) {
            yield todos[i];
        }
    }

    // Clear cache
    clearCache() {
        this.cache.clear();
    }
}
