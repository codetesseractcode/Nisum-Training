// Task 8: Filter Users by City
// Extract unique city names and filter users by selected city

// References to DOM elements
const userContainer = document.getElementById('userContainer');
const filterContainer = document.getElementById('filterContainer');
const cityFilter = document.getElementById('cityFilter');

// Store all users for filtering
let allUsers = [];

// Function to fetch users from the API
function fetchUsers() {
    // Show loading message
    userContainer.innerHTML = '<p class="loading">Loading users...</p>';
    filterContainer.style.display = 'none';
    
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
            allUsers = users; // Store all users for filtering
            populateCityDropdown(users);
            displayUsers(users);
            filterContainer.style.display = 'block'; // Show filter dropdown
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

// Function to extract unique city names and populate dropdown
function populateCityDropdown(users) {
    // Extract all cities and remove duplicates
    const cities = [...new Set(users.map(user => user.address.city))].sort();
    
    // Clear existing options (except "All Cities")
    cityFilter.innerHTML = '<option value="">All Cities</option>';
    
    // Add each city as an option
    cities.forEach(city => {
        const option = document.createElement('option');
        option.value = city;
        option.textContent = city;
        cityFilter.appendChild(option);
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
            <div class="user-details">
                <div>Email: ${user.email}</div>
                <div>City: ${user.address.city}</div>
                <div>Company: ${user.company.name}</div>
            </div>
        `;
        
        userList.appendChild(listItem);
    });
    
    // Clear the container and add the list
    userContainer.innerHTML = '';
    userContainer.appendChild(userList);
}

// Function to filter users by selected city
function filterUsersByCity(city) {
    let filteredUsers;
    
    if (city === '') {
        // If "All Cities" is selected, show all users
        filteredUsers = allUsers;
    } else {
        // Filter users by the selected city
        filteredUsers = allUsers.filter(user => user.address.city === city);
    }
    
    // Display the filtered users
    displayUsers(filteredUsers);
}

// Event Listeners
document.getElementById('fetchBtn').addEventListener('click', fetchUsers);

// Add change event listener to the city filter
cityFilter.addEventListener('change', function() {
    filterUsersByCity(this.value);
});
