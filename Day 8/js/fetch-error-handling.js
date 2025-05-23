// Task 13: Fetch API with Error Handling
// Use the Fetch API to get data from an API endpoint with try...catch error handling

// DOM Elements
const fetchBtn = document.getElementById('fetchBtn');
const resultContainer = document.getElementById('resultContainer');

// Function to fetch data from the API
async function fetchData() {
    // Show loading state
    resultContainer.innerHTML = '<div class="loading">Loading data...</div>';
    
    try {
        // Make the fetch request
        const response = await fetch('https://api.publicapis.org/entries');
        
        // Check if the response is ok (status 200-299)
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        
        // Parse the JSON response
        const data = await response.json();
        
        // Display the data
        displayData(data);
    } catch (error) {
        // Handle any errors
        console.error('Error fetching data:', error);
        
        // Display error message
        resultContainer.innerHTML = `
            <div class="error">
                <p><strong>Error fetching data:</strong> ${error.message}</p>
                <p>Please try again later.</p>
            </div>
        `;
    }
}

// Function to display the fetched data
function displayData(data) {
    // Check if we have entries
    if (!data || !data.entries || data.entries.length === 0) {
        resultContainer.innerHTML = '<p>No entries found.</p>';
        return;
    }
    
    // Create a container for the API results
    const apiResult = document.createElement('div');
    apiResult.className = 'api-result';
    
    // Add header information
    apiResult.innerHTML = `
        <h3>Public APIs</h3>
        <p>Found ${data.count} APIs</p>
    `;
    
    // Create a container for the API cards
    const apiList = document.createElement('div');
    
    // Add the first 20 entries (or limit to avoid too many)
    const entriesToShow = data.entries.slice(0, 20);
    
    entriesToShow.forEach(api => {
        const apiCard = document.createElement('div');
        apiCard.className = 'api-card';
        
        apiCard.innerHTML = `
            <div class="api-name">${api.API}</div>
            <div class="api-description">${api.Description}</div>
            <div>Category: ${api.Category}</div>
            <div>Auth: ${api.Auth || 'None'}</div>
            <div class="api-link">
                <a href="${api.Link}" target="_blank">View Documentation</a>
            </div>
        `;
        
        apiList.appendChild(apiCard);
    });
    
    // Add the list to the result container
    apiResult.appendChild(apiList);
    
    // Add information about limited display
    if (data.entries.length > 20) {
        const moreInfo = document.createElement('p');
        moreInfo.textContent = `Showing 20 of ${data.entries.length} APIs`;
        apiResult.appendChild(moreInfo);
    }
    
    // Clear the container and add the result
    resultContainer.innerHTML = '';
    resultContainer.appendChild(apiResult);
}

// Add click event listener to the fetch button
fetchBtn.addEventListener('click', fetchData);
