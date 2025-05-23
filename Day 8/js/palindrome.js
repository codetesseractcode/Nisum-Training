// Task 11: Palindrome Checker
// Write a function that checks if a string is a palindrome

/**
 * Check if a string is a palindrome
 * @param {string} str - The string to check
 * @return {boolean} - True if the string is a palindrome, false otherwise
 */
function isPalindrome(str) {
    // Remove non-alphanumeric characters and convert to lowercase
    const cleanStr = str.replace(/[^a-zA-Z0-9]/g, '').toLowerCase();
    
    // Reverse the string
    const reversedStr = cleanStr.split('').reverse().join('');
    
    // Compare the original and reversed strings
    return cleanStr === reversedStr;
}

// Alternative implementation using a loop
function isPalindromeLoop(str) {
    // Remove non-alphanumeric characters and convert to lowercase
    const cleanStr = str.replace(/[^a-zA-Z0-9]/g, '').toLowerCase();
    
    // Check characters from both ends moving inward
    for (let i = 0; i < cleanStr.length / 2; i++) {
        if (cleanStr[i] !== cleanStr[cleanStr.length - 1 - i]) {
            return false;
        }
    }
    
    return true;
}

// DOM Elements
const textInput = document.getElementById('textInput');
const checkBtn = document.getElementById('checkBtn');
const result = document.getElementById('result');

// Event listener for the check button
checkBtn.addEventListener('click', function() {
    const text = textInput.value.trim();
    
    if (text === '') {
        alert('Please enter a word or phrase.');
        return;
    }
    
    const palindromeResult = isPalindrome(text);
    
    // Update the result display
    if (palindromeResult) {
        result.textContent = `"${text}" is a palindrome!`;
        result.className = 'result is-palindrome';
    } else {
        result.textContent = `"${text}" is not a palindrome.`;
        result.className = 'result not-palindrome';
    }
    
    result.style.display = 'block';
});

// Event listener for Enter key in the input field
textInput.addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        checkBtn.click();
    }
});
