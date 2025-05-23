// Task 5: Array Methods Challenge
// Use .filter() and .map() to return a new array of the squares of even numbers

// Function to process the array and display results
function processArray(numbersArray) {
    // Display the original array
    document.getElementById('original').textContent = numbersArray.join(', ');
    
    // Filter to get only even numbers
    const evenNumbers = numbersArray.filter(num => num % 2 === 0);
    document.getElementById('evenOnly').textContent = evenNumbers.join(', ');
    
    // Map to get squares of even numbers
    const squaresOfEven = evenNumbers.map(num => num * num);
    document.getElementById('squaredEvens').textContent = squaresOfEven.join(', ');
    
    return squaresOfEven;
}

// Event listener for the process button
document.getElementById('processBtn').addEventListener('click', function() {
    // Get the numbers from the input field
    const inputValue = document.getElementById('numbers').value;
    
    // Convert the input string to an array of numbers
    const numbersArray = inputValue
        .split(',')
        .map(item => parseInt(item.trim()))
        .filter(num => !isNaN(num)); // Remove any non-number entries
    
    if (numbersArray.length > 0) {
        processArray(numbersArray);
    } else {
        alert('Please enter valid numbers separated by commas.');
    }
});
