// todo-validator.js - Validation utility for Todo items
export class TodoValidator {
    constructor() {
        // Using Symbol for private constants (ES6 feature #15)
        this.MIN_LENGTH = Symbol('minLength');
        this.MAX_LENGTH = Symbol('maxLength');

        // Set validation rules
        this[this.MIN_LENGTH] = 1;
        this[this.MAX_LENGTH] = 100;
    }

    // Validate a todo title
    isValid(title, description = '') {
        // Using Object.is to check for empty string (ES6 feature #17)
        if (Object.is(title, '') || title === null || title === undefined) {
            return false;
        }

        // Check title length
        if (title.length < this[this.MIN_LENGTH] || title.length > this[this.MAX_LENGTH]) {
            return false;
        }

        return true;
    }

    // Validate a todo object
    validateTodo(todo) {
        // Using destructuring to extract properties (ES6 feature #5)
        const { title, description = '' } = todo;

        return this.isValid(title, description);
    }

    // Get validation error message
    getErrorMessage(todo) {
        const { title, description = '' } = todo;

        if (Object.is(title, '') || title === null || title === undefined) {
            return 'Title is required';
        }

        if (title.length < this[this.MIN_LENGTH]) {
            return `Title must be at least ${this[this.MIN_LENGTH]} characters`;
        }

        if (title.length > this[this.MAX_LENGTH]) {
            return `Title must be less than ${this[this.MAX_LENGTH]} characters`;
        }

        return '';
    }
}

