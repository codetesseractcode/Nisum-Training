// Task 7: Fetch and Display User List
// Use the Fetch API to get user data and display names and emails in a list

// Reference to the container element
const userContainer = document.getElementById('userContainer');

// Function to fetch users from the API
function fetchUsers() {
    // Show loading message
    userContainer.innerHTML = '<p class="loading">Loading users...</p>';
    
    // Fetch data from the API
    fetch('https://jsonplaceholder.typicode.com/users')
        .then(response => {
            // Check if the request was successful
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(users => {
            displayUsers(users);
        })
        .catch(error => {
            // Handle any errors
            console.error('Error fetching users:', error);
            userContainer.innerHTML = `
                <div class="error">
                    <p>Failed to fetch users: ${error.message}</p>
                </div>
            `;
        });
}

// Function to display the users in the DOM
function displayUsers(users) {
    // Check if users array is empty
    if (!users || users.length === 0) {
        userContainer.innerHTML = '<p>No users found.</p>';
        return;
    }
    
    // Create a list element
    const userList = document.createElement('ul');
    userList.className = 'user-list';
    
    // Add each user to the list
    users.forEach(user => {
        const listItem = document.createElement('li');
        listItem.className = 'user-item';
        
        listItem.innerHTML = `
            <div class="user-name">${user.name}</div>
            <div class="user-email">${user.email}</div>
        `;
        
        userList.appendChild(listItem);
    });
    
    // Clear the container and add the list
    userContainer.innerHTML = '';
    userContainer.appendChild(userList);
}

// Add event listener to the fetch button
document.getElementById('fetchBtn').addEventListener('click', fetchUsers);
