// Task 10: Simple Form Validation
// Implement validations for name, email, and password fields with appropriate error messages

// DOM Elements
const form = document.getElementById('registrationForm');
const nameInput = document.getElementById('name');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
const nameError = document.getElementById('nameError');
const emailError = document.getElementById('emailError');
const passwordError = document.getElementById('passwordError');
const successMessage = document.getElementById('successMessage');

// Validation functions
function validateName() {
    const name = nameInput.value.trim();
    
    if (name === '') {
        showError(nameInput, nameError, 'Name cannot be empty');
        return false;
    }
    
    hideError(nameInput, nameError);
    return true;
}

function validateEmail() {
    const email = emailInput.value.trim();
    // Simple regex for email validation
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    
    if (!emailRegex.test(email)) {
        showError(emailInput, emailError, 'Please enter a valid email address');
        return false;
    }
    
    hideError(emailInput, emailError);
    return true;
}

function validatePassword() {
    const password = passwordInput.value.trim();
    
    if (password.length < 6) {
        showError(passwordInput, passwordError, 'Password must be at least 6 characters');
        return false;
    }
    
    hideError(passwordInput, passwordError);
    return true;
}

// Helper functions to show/hide errors
function showError(inputElement, errorElement, message) {
    inputElement.classList.add('error');
    errorElement.textContent = message;
    errorElement.classList.add('visible');
}

function hideError(inputElement, errorElement) {
    inputElement.classList.remove('error');
    errorElement.classList.remove('visible');
}

// Form submission handler
form.addEventListener('submit', function(e) {
    e.preventDefault();
    
    // Validate all fields
    const isNameValid = validateName();
    const isEmailValid = validateEmail();
    const isPasswordValid = validatePassword();
    
    // If all validations pass, show success message
    if (isNameValid && isEmailValid && isPasswordValid) {
        successMessage.style.display = 'block';
        form.reset(); // Reset the form
    } else {
        successMessage.style.display = 'none';
    }
});

// Real-time validation on input
nameInput.addEventListener('blur', validateName);
emailInput.addEventListener('blur', validateEmail);
passwordInput.addEventListener('blur', validatePassword);

// Clear error on focus
nameInput.addEventListener('focus', () => hideError(nameInput, nameError));
emailInput.addEventListener('focus', () => hideError(emailInput, emailError));
passwordInput.addEventListener('focus', () => hideError(passwordInput, passwordError));
