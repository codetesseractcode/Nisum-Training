import { createSlice } from '@reduxjs/toolkit';

// Mock product data - following YAGNI principle
const mockProducts = [
  {
    id: 1,
    name: 'Wireless Bluetooth Headphones',
    price: 79.99,
    image: '🎧',
    description: 'High-quality wireless headphones with noise cancellation',
    category: 'Electronics',
    stock: 15,
    rating: 4.5
  },
  {
    id: 2,
    name: 'Smart Watch',
    price: 199.99,
    image: '⌚',
    description: 'Feature-rich smartwatch with health tracking',
    category: 'Electronics',
    stock: 8,
    rating: 4.3
  },
  {
    id: 3,
    name: 'Laptop Backpack',
    price: 49.99,
    image: '🎒',
    description: 'Durable laptop backpack with multiple compartments',
    category: 'Accessories',
    stock: 20,
    rating: 4.7
  },
  {
    id: 4,
    name: 'Wireless Mouse',
    price: 29.99,
    image: '🖱️',
    description: 'Ergonomic wireless mouse with precision tracking',
    category: 'Electronics',
    stock: 25,
    rating: 4.2
  },
  {
    id: 5,
    name: 'USB-C Cable',
    price: 19.99,
    image: '🔌',
    description: 'High-speed USB-C charging cable',
    category: 'Accessories',
    stock: 30,
    rating: 4.0
  },
  {
    id: 6,
    name: 'Bluetooth Speaker',
    price: 89.99,
    image: '🔊',
    description: 'Portable Bluetooth speaker with premium sound',
    category: 'Electronics',
    stock: 12,
    rating: 4.6
  },
  {
    id: 7,
    name: 'Phone Case',
    price: 24.99,
    image: '📱',
    description: 'Protective phone case with card slots',
    category: 'Accessories',
    stock: 40,
    rating: 4.1
  },
  {
    id: 8,
    name: 'Mechanical Keyboard',
    price: 129.99,
    image: '⌨️',
    description: 'RGB mechanical keyboard for gaming',
    category: 'Electronics',
    stock: 6,
    rating: 4.8
  }
];

// Initial state
const initialState = {
  items: mockProducts,
  filteredItems: mockProducts,
  categories: ['All', 'Electronics', 'Accessories'],
  selectedCategory: 'All',
  searchTerm: '',
  sortBy: 'name',
  isLoading: false
};

// Products slice - following Single Responsibility Principle
const productsSlice = createSlice({
  name: 'products',
  initialState,
  reducers: {
    // Filter by category
    filterByCategory: (state, action) => {
      state.selectedCategory = action.payload;
      state.filteredItems = action.payload === 'All' 
        ? state.items 
        : state.items.filter(item => item.category === action.payload);
      
      // Apply search filter if exists
      if (state.searchTerm) {
        state.filteredItems = state.filteredItems.filter(item =>
          item.name.toLowerCase().includes(state.searchTerm.toLowerCase())
        );
      }
    },
    
    // Search products
    searchProducts: (state, action) => {
      state.searchTerm = action.payload;
      let filtered = state.selectedCategory === 'All' 
        ? state.items 
        : state.items.filter(item => item.category === state.selectedCategory);
      
      if (action.payload) {
        filtered = filtered.filter(item =>
          item.name.toLowerCase().includes(action.payload.toLowerCase())
        );
      }
      
      state.filteredItems = filtered;
    },
    
    // Sort products
    sortProducts: (state, action) => {
      state.sortBy = action.payload;
      state.filteredItems = [...state.filteredItems].sort((a, b) => {
        switch (action.payload) {
          case 'name':
            return a.name.localeCompare(b.name);
          case 'price-low':
            return a.price - b.price;
          case 'price-high':
            return b.price - a.price;
          case 'rating':
            return b.rating - a.rating;
          default:
            return 0;
        }
      });
    },
    
    // Update product stock (when item is purchased)
    updateProductStock: (state, action) => {
      const { id, quantity } = action.payload;
      const product = state.items.find(item => item.id === id);
      if (product && product.stock >= quantity) {
        product.stock -= quantity;
      }
    },
    
    // Clear filters
    clearFilters: (state) => {
      state.selectedCategory = 'All';
      state.searchTerm = '';
      state.filteredItems = state.items;
    }
  }
});

// Action creators
export const {
  filterByCategory,
  searchProducts,
  sortProducts,
  updateProductStock,
  clearFilters
} = productsSlice.actions;

// Selectors - following Interface Segregation Principle
export const selectAllProducts = (state) => state.products.items;
export const selectFilteredProducts = (state) => state.products.filteredItems;
export const selectCategories = (state) => state.products.categories;
export const selectSelectedCategory = (state) => state.products.selectedCategory;
export const selectSearchTerm = (state) => state.products.searchTerm;
export const selectSortBy = (state) => state.products.sortBy;

// Complex selectors
export const selectProductById = (state, id) => 
  state.products.items.find(item => item.id === id);

export const selectProductsByCategory = (state, category) =>
  state.products.items.filter(item => item.category === category);

export const selectFeaturedProducts = (state) =>
  state.products.items.filter(item => item.rating >= 4.5);

export default productsSlice.reducer;
