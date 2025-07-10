import { createSlice } from '@reduxjs/toolkit';

// Initial state
const initialState = {
  items: [],
  totalQuantity: 0,
  totalAmount: 0,
  isOpen: false
};

// Helper function to calculate totals
const calculateTotals = (items) => {
  const totalQuantity = items.reduce((total, item) => total + item.quantity, 0);
  const totalAmount = items.reduce((total, item) => total + (item.price * item.quantity), 0);
  return { totalQuantity, totalAmount };
};

// Cart slice - following Single Responsibility Principle
const cartSlice = createSlice({
  name: 'cart',
  initialState,
  reducers: {
    // Add item to cart
    addToCart: (state, action) => {
      const newItem = action.payload;
      const existingItem = state.items.find(item => item.id === newItem.id);
      
      if (existingItem) {
        existingItem.quantity += 1;
      } else {
        state.items.push({
          ...newItem,
          quantity: 1
        });
      }
      
      const totals = calculateTotals(state.items);
      state.totalQuantity = totals.totalQuantity;
      state.totalAmount = totals.totalAmount;
    },
    
    // Remove item from cart
    removeFromCart: (state, action) => {
      const id = action.payload;
      state.items = state.items.filter(item => item.id !== id);
      
      const totals = calculateTotals(state.items);
      state.totalQuantity = totals.totalQuantity;
      state.totalAmount = totals.totalAmount;
    },
    
    // Update item quantity
    updateQuantity: (state, action) => {
      const { id, quantity } = action.payload;
      const item = state.items.find(item => item.id === id);
      
      if (item) {
        if (quantity <= 0) {
          state.items = state.items.filter(item => item.id !== id);
        } else {
          item.quantity = quantity;
        }
        
        const totals = calculateTotals(state.items);
        state.totalQuantity = totals.totalQuantity;
        state.totalAmount = totals.totalAmount;
      }
    },
    
    // Increment item quantity
    incrementQuantity: (state, action) => {
      const id = action.payload;
      const item = state.items.find(item => item.id === id);
      
      if (item) {
        item.quantity += 1;
        const totals = calculateTotals(state.items);
        state.totalQuantity = totals.totalQuantity;
        state.totalAmount = totals.totalAmount;
      }
    },
    
    // Decrement item quantity
    decrementQuantity: (state, action) => {
      const id = action.payload;
      const item = state.items.find(item => item.id === id);
      
      if (item && item.quantity > 1) {
        item.quantity -= 1;
        const totals = calculateTotals(state.items);
        state.totalQuantity = totals.totalQuantity;
        state.totalAmount = totals.totalAmount;
      }
    },
    
    // Clear cart
    clearCart: (state) => {
      state.items = [];
      state.totalQuantity = 0;
      state.totalAmount = 0;
    },
    
    // Toggle cart visibility
    toggleCart: (state) => {
      state.isOpen = !state.isOpen;
    },
    
    // Close cart
    closeCart: (state) => {
      state.isOpen = false;
    }
  }
});

// Action creators
export const {
  addToCart,
  removeFromCart,
  updateQuantity,
  incrementQuantity,
  decrementQuantity,
  clearCart,
  toggleCart,
  closeCart
} = cartSlice.actions;

// Selectors - following Interface Segregation Principle
export const selectCartItems = (state) => state.cart.items;
export const selectCartTotalQuantity = (state) => state.cart.totalQuantity;
export const selectCartTotalAmount = (state) => state.cart.totalAmount;
export const selectIsCartOpen = (state) => state.cart.isOpen;
export const selectCartItemCount = (state) => state.cart.items.length;

// Complex selectors
export const selectCartItemById = (state, id) => 
  state.cart.items.find(item => item.id === id);

export const selectCartSummary = (state) => ({
  items: state.cart.items,
  totalQuantity: state.cart.totalQuantity,
  totalAmount: state.cart.totalAmount,
  itemCount: state.cart.items.length
});

export default cartSlice.reducer;
