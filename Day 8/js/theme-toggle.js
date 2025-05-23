// Task 14: Toggle Light/Dark Mode
// Create a button that toggles the background color of the webpage between light and dark modes

// DOM Elements
const themeToggleBtn = document.getElementById('themeToggle');
const toggleIcon = document.querySelector('.toggle-icon');
const toggleText = document.querySelector('.toggle-text');

// Check for saved theme preference
document.addEventListener('DOMContentLoaded', () => {
    // Check if theme preference is saved in localStorage
    const savedTheme = localStorage.getItem('theme');
    
    if (savedTheme === 'dark') {
        enableDarkMode();
    } else {
        enableLightMode(); // Default or if 'light' is saved
    }
});

// Toggle between light and dark mode
themeToggleBtn.addEventListener('click', () => {
    // Check current theme
    const isDarkMode = document.body.classList.contains('dark-mode');
    
    if (isDarkMode) {
        enableLightMode();
    } else {
        enableDarkMode();
    }
});

// Function to enable dark mode
function enableDarkMode() {
    // Add dark mode class to body
    document.body.classList.remove('light-mode');
    document.body.classList.add('dark-mode');
    
    // Update button icon and text
    toggleIcon.textContent = '☀️';
    toggleText.textContent = 'Light Mode';
    
    // Save preference to localStorage
    localStorage.setItem('theme', 'dark');
}

// Function to enable light mode
function enableLightMode() {
    // Add light mode class to body
    document.body.classList.remove('dark-mode');
    document.body.classList.add('light-mode');
    
    // Update button icon and text
    toggleIcon.textContent = '🌙';
    toggleText.textContent = 'Dark Mode';
    
    // Save preference to localStorage
    localStorage.setItem('theme', 'light');
}
