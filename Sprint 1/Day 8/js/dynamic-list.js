// Task 15: Dynamic List Creation
// Take input from a text field and add it as a new item in an unordered list on button click

// DOM Elements
const itemInput = document.getElementById('itemInput');
const addBtn = document.getElementById('addBtn');
const itemList = document.getElementById('itemList');
const emptyList = document.getElementById('emptyList');

// Function to add a new item to the list
function addItem() {
    // Get the value from the input field
    const itemText = itemInput.value.trim();
    
    if (itemText !== '') {
        // Hide the empty list message
        emptyList.style.display = 'none';
        
        // Create a new list item
        const li = document.createElement('li');
        
        // Create text node with the item text
        const textNode = document.createTextNode(itemText);
        li.appendChild(textNode);
        
        // Create delete button for the item
        const deleteBtn = document.createElement('button');
        deleteBtn.textContent = 'Delete';
        deleteBtn.className = 'delete-btn';
        deleteBtn.addEventListener('click', function() {
            li.remove();
            
            // Show empty list message if no items remain
            if (itemList.children.length === 0) {
                emptyList.style.display = 'block';
            }
        });
        
        // Add delete button to the list item
        li.appendChild(deleteBtn);
        
        // Add the new item to the list
        itemList.appendChild(li);
        
        // Clear the input field
        itemInput.value = '';
        
        // Focus on input field for next entry
        itemInput.focus();
    } else {
        alert('Please enter an item.');
    }
}

// Event listeners
addBtn.addEventListener('click', addItem);

// Allow adding items by pressing Enter key
itemInput.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        addItem();
    }
});
