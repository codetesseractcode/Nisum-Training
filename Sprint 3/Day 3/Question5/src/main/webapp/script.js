// Global variables
let currentPage = 0;
let pageSize = 10;
let totalProducts = 0;
let editMode = false;

// DOM Elements
const productForm = document.getElementById('product-form');
const productsTable = document.getElementById('products-table');
const productsBody = document.getElementById('products-body');
const addProductButton = document.getElementById('add-product');
const cancelButton = document.getElementById('cancel-button');
const prevPageButton = document.getElementById('prev-page');
const nextPageButton = document.getElementById('next-page');
const currentPageSpan = document.getElementById('current-page');
const pageSizeSelect = document.getElementById('page-size');
const applyFiltersButton = document.getElementById('apply-filters');
const applySortButton = document.getElementById('apply-sort');

// API Base URL
const API_URL = '/api/products';

// Event Listeners
document.addEventListener('DOMContentLoaded', () => {
    loadProducts();
    setupEventListeners();
});

function setupEventListeners() {
    // Form submission
    productForm.addEventListener('submit', handleFormSubmit);

    // Add product button
    addProductButton.addEventListener('click', () => {
        resetForm();
        showForm();
    });

    // Cancel button
    cancelButton.addEventListener('click', () => {
        hideForm();
    });

    // Pagination
    prevPageButton.addEventListener('click', () => {
        if (currentPage > 0) {
            currentPage--;
            loadProducts();
        }
    });

    nextPageButton.addEventListener('click', () => {
        currentPage++;
        loadProducts();
    });

    pageSizeSelect.addEventListener('change', () => {
        pageSize = parseInt(pageSizeSelect.value);
        currentPage = 0;
        loadProducts();
    });

    // Filters and sorting
    applyFiltersButton.addEventListener('click', () => {
        currentPage = 0;
        loadProducts();
    });

    applySortButton.addEventListener('click', () => {
        currentPage = 0;
        loadProducts();
    });
}

// API Functions
async function loadProducts() {
    try {
        // Get filter values
        const category = document.getElementById('category-filter').value;
        const minPrice = document.getElementById('min-price').value;
        const maxPrice = document.getElementById('max-price').value;

        // Get sort values
        const sortBy = document.getElementById('sort-by').value;
        const sortDirection = document.getElementById('sort-direction').value;

        // Build query params
        let url = `${API_URL}?page=${currentPage}&size=${pageSize}`;

        if (sortBy) {
            url += `&sort=${sortBy}&direction=${sortDirection}`;
        }

        if (category) {
            url += `&category=${encodeURIComponent(category)}`;
        }

        if (minPrice) {
            url += `&minPrice=${minPrice}`;
        }

        if (maxPrice) {
            url += `&maxPrice=${maxPrice}`;
        }

        const response = await fetch(url);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const products = await response.json();
        renderProducts(products);

        // Update pagination
        updatePaginationControls();
    } catch (error) {
        console.error('Error loading products:', error);
        alert('Failed to load products. Please try again later.');
    }
}

async function createProduct(product) {
    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Failed to create product');
        }

        const createdProduct = await response.json();
        alert('Product created successfully!');
        return createdProduct;
    } catch (error) {
        console.error('Error creating product:', error);
        alert(`Failed to create product: ${error.message}`);
        throw error;
    }
}

async function updateProduct(id, product) {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Failed to update product');
        }

        const updatedProduct = await response.json();
        alert('Product updated successfully!');
        return updatedProduct;
    } catch (error) {
        console.error('Error updating product:', error);
        alert(`Failed to update product: ${error.message}`);
        throw error;
    }
}

async function deleteProduct(id) {
    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Failed to delete product');
        }

        alert('Product deleted successfully!');
    } catch (error) {
        console.error('Error deleting product:', error);
        alert(`Failed to delete product: ${error.message}`);
        throw error;
    }
}

// UI Functions
function renderProducts(products) {
    productsBody.innerHTML = '';

    if (products.length === 0) {
        const emptyRow = document.createElement('tr');
        emptyRow.innerHTML = '<td colspan="7" style="text-align: center;">No products found</td>';
        productsBody.appendChild(emptyRow);
        return;
    }

    products.forEach(product => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>$${product.price.toFixed(2)}</td>
            <td>${product.stockQuantity}</td>
            <td>${product.category}</td>
            <td class="action-buttons">
                <button class="edit-btn" data-id="${product.id}">Edit</button>
                <button class="delete-btn" data-id="${product.id}">Delete</button>
            </td>
        `;
        productsBody.appendChild(row);
    });

    // Add event listeners to edit and delete buttons
    document.querySelectorAll('.edit-btn').forEach(button => {
        button.addEventListener('click', handleEditClick);
    });

    document.querySelectorAll('.delete-btn').forEach(button => {
        button.addEventListener('click', handleDeleteClick);
    });
}

function updatePaginationControls() {
    currentPageSpan.textContent = `Page ${currentPage + 1}`;
    prevPageButton.disabled = currentPage === 0;

    // We don't know if there are more pages unless we have a total count from the backend
    // For simplicity, we'll enable the next button if the current page has items
    nextPageButton.disabled = productsBody.children.length < pageSize;
}

function handleFormSubmit(event) {
    event.preventDefault();

    const productId = document.getElementById('product-id').value;
    const product = {
        name: document.getElementById('name').value,
        description: document.getElementById('description').value,
        price: parseFloat(document.getElementById('price').value),
        stockQuantity: parseInt(document.getElementById('stock-quantity').value),
        category: document.getElementById('category').value
    };

    if (editMode && productId) {
        updateProduct(productId, product)
            .then(() => {
                hideForm();
                loadProducts();
            })
            .catch(error => console.error('Error in update:', error));
    } else {
        createProduct(product)
            .then(() => {
                hideForm();
                loadProducts();
            })
            .catch(error => console.error('Error in create:', error));
    }
}

async function handleEditClick(event) {
    const productId = event.target.dataset.id;
    try {
        const response = await fetch(`${API_URL}/${productId}`);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const product = await response.json();
        fillFormForEdit(product);
    } catch (error) {
        console.error('Error fetching product for edit:', error);
        alert('Failed to load product details for editing.');
    }
}

function handleDeleteClick(event) {
    const productId = event.target.dataset.id;
    if (confirm('Are you sure you want to delete this product?')) {
        deleteProduct(productId)
            .then(() => loadProducts())
            .catch(error => console.error('Error in delete:', error));
    }
}

function fillFormForEdit(product) {
    document.getElementById('product-id').value = product.id;
    document.getElementById('name').value = product.name;
    document.getElementById('description').value = product.description;
    document.getElementById('price').value = product.price;
    document.getElementById('stock-quantity').value = product.stockQuantity;
    document.getElementById('category').value = product.category;

    editMode = true;
    document.getElementById('save-button').textContent = 'Update Product';
    showForm();
}

function resetForm() {
    productForm.reset();
    document.getElementById('product-id').value = '';
    editMode = false;
    document.getElementById('save-button').textContent = 'Save Product';
}

function showForm() {
    document.querySelector('.product-form-container').classList.remove('hidden');
}

function hideForm() {
    document.querySelector('.product-form-container').classList.add('hidden');
    resetForm();
}
