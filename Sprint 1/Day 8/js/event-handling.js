// Task 2: Event Handling
// Add an event listener to a button with ID 'submitBtn' that shows an alert saying 'Submitted!' when clicked

// Get a reference to the button
const submitButton = document.getElementById('submitBtn');

// Add click event listener to the button
submitButton.addEventListener('click', function() {
    alert('Submitted!');
});
