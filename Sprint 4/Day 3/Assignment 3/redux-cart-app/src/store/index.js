import { configureStore } from '@reduxjs/toolkit';
import userReducer from './slices/userSlice';
import cartReducer from './slices/cartSlice';
import productsReducer from './slices/productsSlice';

// Configure Redux store - following Single Responsibility Principle
export const store = configureStore({
  reducer: {
    user: userReducer,
    cart: cartReducer,
    products: productsReducer
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: {
        ignoredActions: ['persist/PERSIST']
      }
    })
});
