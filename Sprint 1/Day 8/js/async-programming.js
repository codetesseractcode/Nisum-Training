// Task 3: Asynchronous Programming
// Use setTimeout to log 'Hello, JavaScript!' after 2 seconds

// Log a message immediately
console.log('Starting the timeout...');

// Function that will execute after the delay
function displayMessage() {
    console.log('Hello, JavaScript!');
    
    // Also update the UI to show the message
    document.getElementById('output').textContent = 'Hello, JavaScript!';
}

// Add event listener to the button
document.getElementById('triggerBtn').addEventListener('click', function() {
    document.getElementById('output').textContent = 'Waiting for 2 seconds...';
    
    // Use setTimeout to delay execution
    setTimeout(displayMessage, 2000); // 2000 milliseconds = 2 seconds
});
