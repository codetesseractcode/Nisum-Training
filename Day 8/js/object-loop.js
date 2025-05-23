// Task 12: Loop Through an Object
// Given an object with key-value pairs, loop through it and print each key and value

// Sample user object
const user = {
    name: "Alice",
    age: 25,
    email: "alice@example.com"
};

// Function to loop through an object and display its properties
function loopThroughObject(obj) {
    // Get the container for displaying key-value pairs
    const container = document.getElementById('keyValueContainer');
    container.innerHTML = '';
    
    // Loop through the object using for...in
    for (const key in obj) {
        if (obj.hasOwnProperty(key)) {
            const value = obj[key];
            
            // Create a div to display the key-value pair
            const keyValueDiv = document.createElement('div');
            keyValueDiv.className = 'key-value';
            keyValueDiv.innerHTML = `<span class="key">${key}:</span> <span class="value">${value}</span>`;
            
            // Add the div to the container
            container.appendChild(keyValueDiv);
            
            // Also log to the console
            console.log(`${key}: ${value}`);
        }
    }
    
    // Show the output container
    document.getElementById('output').style.display = 'block';
}

// Alternative ways to loop through objects
function alternativeObjectLoops(obj) {
    console.log("Using Object.keys():");
    Object.keys(obj).forEach(key => {
        console.log(`${key}: ${obj[key]}`);
    });
    
    console.log("\nUsing Object.entries():");
    Object.entries(obj).forEach(([key, value]) => {
        console.log(`${key}: ${value}`);
    });
}

// Event listener for the button
document.getElementById('loopBtn').addEventListener('click', function() {
    loopThroughObject(user);
    
    // Also demonstrate alternative methods in console
    alternativeObjectLoops(user);
});
