// todo-stats.js - Statistics utility for Todo items
export class TodoStats {
    constructor() {
        // Using WeakSet to track processed todos (ES6 feature #14)
        this.processedTodos = new WeakSet();

        // Initialize stats using Object.assign (ES6 feature #16)
        this.stats = Object.assign({}, {
            total: 0,
            completed: 0,
            active: 0,
            completionRate: 0
        });
    }

    // Update statistics based on todos
    updateStats() {
        // Get all todos from the DOM
        const todoItems = document.querySelectorAll('.todo-item');
        const completedItems = document.querySelectorAll('.todo-item.completed');

        // Update stats
        Object.assign(this.stats, {
            total: todoItems.length,
            completed: completedItems.length,
            active: todoItems.length - completedItems.length,
            completionRate: todoItems.length ? (completedItems.length / todoItems.length) * 100 : 0
        });

        console.log(`Statistics updated: ${this.getStatsMessage()}`);
        return this.stats;
    }

    // Get formatted stats message using template literals
    getStatsMessage() {
        return `Total: ${this.stats.total}, Active: ${this.stats.active}, Completed: ${this.stats.completed}, Completion Rate: ${this.stats.completionRate.toFixed(1)}%`;
    }

    // Process a collection of todos and mark them as processed
    processTodos(todos) {
        // Using for...of loop (ES6 feature #11)
        for (const todo of todos) {
            this.processedTodos.add(todo);
        }
    }

    // Check if a todo has been processed
    isProcessed(todo) {
        return this.processedTodos.has(todo);
    }

    // Generator function to iterate through statistics (ES6 feature #12)
    *statsIterator() {
        const entries = Object.entries(this.stats);
        for (let i = 0; i < entries.length; i++) {
            yield entries[i];
        }
    }
}

