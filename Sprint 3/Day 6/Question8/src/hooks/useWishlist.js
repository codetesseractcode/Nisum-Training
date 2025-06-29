import { useState, useEffect } from 'react';

/**
 * Custom hook for managing wishlist functionality
 * Handles adding/removing items from wishlist and persists data in localStorage
 */
export const useWishlist = () => {
  const [wishlistItems, setWishlistItems] = useState([]);

  // Load wishlist from localStorage on hook initialization
  useEffect(() => {
    const savedWishlist = localStorage.getItem('wishlist');
    if (savedWishlist) {
      try {
        const parsedWishlist = JSON.parse(savedWishlist);
        setWishlistItems(Array.isArray(parsedWishlist) ? parsedWishlist : []);
      } catch (error) {
        console.error('Error parsing wishlist from localStorage:', error);
        setWishlistItems([]);
      }
    }
  }, []);

  // Save wishlist to localStorage whenever it changes
  useEffect(() => {
    localStorage.setItem('wishlist', JSON.stringify(wishlistItems));
  }, [wishlistItems]);

  /**
   * Add a product to the wishlist
   * @param {Object} product - The product to add
   */
  const addToWishlist = (product) => {
    setWishlistItems(prevItems => {
      // Check if item already exists in wishlist
      const existingItem = prevItems.find(item => item.id === product.id);
      if (existingItem) {
        return prevItems; // Item already in wishlist, don't add duplicate
      }
      
      // Add new item to wishlist
      return [...prevItems, {
        id: product.id,
        name: product.name,
        price: product.price,
        image: product.image,
        category: product.category,
        dateAdded: new Date().toISOString()
      }];
    });
  };

  /**
   * Remove a product from the wishlist
   * @param {number} productId - The ID of the product to remove
   */
  const removeFromWishlist = (productId) => {
    setWishlistItems(prevItems => 
      prevItems.filter(item => item.id !== productId)
    );
  };

  /**
   * Toggle a product in the wishlist (add if not present, remove if present)
   * @param {Object} product - The product to toggle
   */
  const toggleWishlist = (product) => {
    const isInWishlist = wishlistItems.some(item => item.id === product.id);
    
    if (isInWishlist) {
      removeFromWishlist(product.id);
    } else {
      addToWishlist(product);
    }
  };

  /**
   * Check if a product is in the wishlist
   * @param {number} productId - The ID of the product to check
   * @returns {boolean} - True if product is in wishlist
   */
  const isInWishlist = (productId) => {
    return wishlistItems.some(item => item.id === productId);
  };

  /**
   * Clear all items from the wishlist
   */
  const clearWishlist = () => {
    setWishlistItems([]);
  };

  /**
   * Get wishlist item count
   * @returns {number} - Number of items in wishlist
   */
  const getWishlistCount = () => {
    return wishlistItems.length;
  };

  return {
    wishlistItems,
    addToWishlist,
    removeFromWishlist,
    toggleWishlist,
    isInWishlist,
    clearWishlist,
    wishlistCount: getWishlistCount()
  };
};
