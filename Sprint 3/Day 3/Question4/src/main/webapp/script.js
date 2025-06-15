// Global variables
let currentPage = 0;
let pageSize = 5;
let totalPages = 0;
let currentAuthorFilter = '';
let currentYearFilter = '';
let editMode = false;
let books = [];

// DOM Elements
const bookForm = document.getElementById('bookForm');
const formTitle = document.getElementById('formTitle');
const saveButton = document.getElementById('saveButton');
const cancelButton = document.getElementById('cancelButton');
const booksTableBody = document.getElementById('booksTableBody');
const prevPageBtn = document.getElementById('prevPage');
const nextPageBtn = document.getElementById('nextPage');
const pageInfo = document.getElementById('pageInfo');
const applyFiltersBtn = document.getElementById('applyFilters');
const clearFiltersBtn = document.getElementById('clearFilters');
const authorFilterInput = document.getElementById('authorFilter');
const yearFilterInput = document.getElementById('yearFilter');
const searchInput = document.getElementById('searchInput');
const searchButton = document.getElementById('searchButton');
const toast = document.getElementById('toast');
const confirmModal = document.getElementById('confirmModal');
const cancelDeleteBtn = document.getElementById('cancelDelete');
const confirmDeleteBtn = document.getElementById('confirmDelete');
const closeModalBtn = document.querySelector('.close-modal');
const deleteBookTitle = document.getElementById('deleteBookTitle');

// Initialize
document.addEventListener('DOMContentLoaded', () => {
    loadBooks();

    // Event listeners
    bookForm.addEventListener('submit', saveBook);
    cancelButton.addEventListener('click', resetForm);
    prevPageBtn.addEventListener('click', () => navigatePage(-1));
    nextPageBtn.addEventListener('click', () => navigatePage(1));
    applyFiltersBtn.addEventListener('click', applyFilters);
    clearFiltersBtn.addEventListener('click', clearFilters);
    searchButton.addEventListener('click', () => {
        currentAuthorFilter = searchInput.value;
        loadBooks();
    });
    searchInput.addEventListener('keypress', (e) => {
        if (e.key === 'Enter') {
            currentAuthorFilter = searchInput.value;
            loadBooks();
        }
    });
    cancelDeleteBtn.addEventListener('click', closeModal);
    closeModalBtn.addEventListener('click', closeModal);
    confirmDeleteBtn.addEventListener('click', performDelete);
});

// API Functions
async function loadBooks() {
    try {
        let url = `/books?page=${currentPage}&size=${pageSize}`;

        if (currentAuthorFilter) {
            url += `&author=${encodeURIComponent(currentAuthorFilter)}`;
        }

        if (currentYearFilter) {
            url += `&publishedYear=${currentYearFilter}`;
        }

        const response = await fetch(url);
        const data = await response.json();

        books = data.books;
        totalPages = data.totalPages;

        renderBooks(books);
        updatePagination();
    } catch (error) {
        showToast(`Error loading books: ${error.message}`, false);
    }
}

async function getBook(id) {
    try {
        const response = await fetch(`/books/${id}`);
        return await response.json();
    } catch (error) {
        showToast(`Error fetching book: ${error.message}`, false);
    }
}

async function createBook(book) {
    try {
        const response = await fetch('/books', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(book)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(formatErrorMessage(errorData));
        }

        return await response.json();
    } catch (error) {
        showToast(`Error creating book: ${error.message}`, false);
        throw error;
    }
}

async function updateBook(id, book) {
    try {
        const response = await fetch(`/books/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(book)
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(formatErrorMessage(errorData));
        }

        return await response.json();
    } catch (error) {
        showToast(`Error updating book: ${error.message}`, false);
        throw error;
    }
}

async function deleteBook(id) {
    try {
        const response = await fetch(`/books/${id}`, {
            method: 'DELETE'
        });

        if (!response.ok) {
            throw new Error('Failed to delete book');
        }

        return true;
    } catch (error) {
        showToast(`Error deleting book: ${error.message}`, false);
        return false;
    }
}

// UI Functions
function renderBooks(books) {
    booksTableBody.innerHTML = '';

    if (books.length === 0) {
        const row = document.createElement('tr');
        row.innerHTML = `<td colspan="5" style="text-align: center;">No books found</td>`;
        booksTableBody.appendChild(row);
        return;
    }

    books.forEach(book => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${book.id}</td>
            <td>${escapeHtml(book.title)}</td>
            <td>${escapeHtml(book.author)}</td>
            <td>${book.publishedYear}</td>
            <td class="action-buttons">
                <button class="edit-btn" onclick="editBookForm(${book.id})">
                    <i class="fas fa-edit"></i>
                </button>
                <button class="delete-btn" onclick="confirmDelete(${book.id})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        `;
        booksTableBody.appendChild(row);
    });
}

function updatePagination() {
    pageInfo.textContent = `Page ${currentPage + 1} of ${Math.max(1, totalPages)}`;

    prevPageBtn.disabled = currentPage <= 0;
    nextPageBtn.disabled = currentPage >= totalPages - 1 || totalPages === 0;

    if (prevPageBtn.disabled) {
        prevPageBtn.classList.add('disabled');
    } else {
        prevPageBtn.classList.remove('disabled');
    }

    if (nextPageBtn.disabled) {
        nextPageBtn.classList.add('disabled');
    } else {
        nextPageBtn.classList.remove('disabled');
    }
}

function navigatePage(direction) {
    const newPage = currentPage + direction;

    if (newPage >= 0 && newPage < totalPages) {
        currentPage = newPage;
        loadBooks();
    }
}

function applyFilters() {
    currentAuthorFilter = authorFilterInput.value;
    currentYearFilter = yearFilterInput.value;
    currentPage = 0;
    loadBooks();
}

function clearFilters() {
    authorFilterInput.value = '';
    yearFilterInput.value = '';
    currentAuthorFilter = '';
    currentYearFilter = '';
    currentPage = 0;
    loadBooks();
}

// Form handling
async function saveBook(e) {
    e.preventDefault();

    const titleInput = document.getElementById('title');
    const authorInput = document.getElementById('author');
    const publishedYearInput = document.getElementById('publishedYear');
    const bookIdInput = document.getElementById('bookId');

    // Simple client-side validation
    if (!titleInput.value.trim()) {
        showToast('Title cannot be empty', false);
        return;
    }

    if (!authorInput.value.trim()) {
        showToast('Author cannot be empty', false);
        return;
    }

    if (!publishedYearInput.value || publishedYearInput.value < 1000) {
        showToast('Published year must be at least 1000', false);
        return;
    }

    const book = {
        title: titleInput.value.trim(),
        author: authorInput.value.trim(),
        publishedYear: parseInt(publishedYearInput.value)
    };

    try {
        if (editMode) {
            const bookId = parseInt(bookIdInput.value);
            await updateBook(bookId, book);
            showToast('Book updated successfully!', true);
        } else {
            await createBook(book);
            showToast('Book added successfully!', true);
        }

        resetForm();
        loadBooks();
    } catch (error) {
        // Error handling is done in the API functions
    }
}

async function editBookForm(id) {
    const book = await getBook(id);

    if (book) {
        document.getElementById('bookId').value = book.id;
        document.getElementById('title').value = book.title;
        document.getElementById('author').value = book.author;
        document.getElementById('publishedYear').value = book.publishedYear;

        formTitle.textContent = 'Edit Book';
        saveButton.textContent = 'Update Book';
        cancelButton.style.display = 'block';

        editMode = true;

        // Scroll to form
        document.querySelector('.form-section').scrollIntoView({ behavior: 'smooth' });
    }
}

function resetForm() {
    bookForm.reset();
    document.getElementById('bookId').value = '';

    formTitle.textContent = 'Add New Book';
    saveButton.textContent = 'Add Book';
    cancelButton.style.display = 'none';

    editMode = false;
}

// Delete handling
function confirmDelete(id) {
    const book = books.find(b => b.id === id);

    if (book) {
        deleteBookTitle.textContent = `"${book.title}" by ${book.author}`;
        confirmModal.style.display = 'flex';
        confirmDeleteBtn.onclick = () => performDelete(id);
    }
}

async function performDelete(id) {
    const success = await deleteBook(id);

    if (success) {
        showToast('Book deleted successfully!', true);
        loadBooks();
    }

    closeModal();
}

function closeModal() {
    confirmModal.style.display = 'none';
}

// Utility functions
function showToast(message, isSuccess = true) {
    const toastIcon = document.querySelector('.toast-icon');
    const toastMessage = document.querySelector('.toast-message');

    if (isSuccess) {
        toastIcon.classList.remove('error');
        toastIcon.classList.add('success');
        toastIcon.classList.remove('fa-times-circle');
        toastIcon.classList.add('fa-check-circle');
    } else {
        toastIcon.classList.remove('success');
        toastIcon.classList.add('error');
        toastIcon.classList.remove('fa-check-circle');
        toastIcon.classList.add('fa-times-circle');
    }

    toastMessage.textContent = message;

    toast.classList.add('show');

    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

function formatErrorMessage(errorData) {
    if (errorData.details) {
        return Object.entries(errorData.details)
            .map(([field, error]) => `${field}: ${error}`)
            .join(', ');
    }

    return errorData.message || 'Unknown error occurred';
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Expose functions to global scope for event handlers
window.editBookForm = editBookForm;
window.confirmDelete = confirmDelete;
window.performDelete = performDelete;
