import React, { createContext, useContext } from 'react';
import { useWishlist } from '../hooks/useWishlist';

// Create the Wishlist Context
const WishlistContext = createContext();

// Custom hook to use the Wishlist Context
export const useWishlistContext = () => {
  const context = useContext(WishlistContext);
  if (!context) {
    throw new Error('useWishlistContext must be used within a WishlistProvider');
  }
  return context;
};

// Wishlist Provider Component
export const WishlistProvider = ({ children }) => {
  const wishlistMethods = useWishlist();

  return (
    <WishlistContext.Provider value={wishlistMethods}>
      {children}
    </WishlistContext.Provider>
  );
};

export default WishlistContext;
