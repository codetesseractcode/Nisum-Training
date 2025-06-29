import React from 'react';
import { useWishlistContext } from '../contexts/WishlistContext';
import './WishlistButton.css';

const WishlistButton = ({ product, size = 'medium', showText = false }) => {
  const { toggleWishlist, isInWishlist } = useWishlistContext();
  
  const inWishlist = isInWishlist(product.id);

  const handleClick = (e) => {
    e.preventDefault();
    e.stopPropagation();
    toggleWishlist(product);
  };

  return (
    <button
      onClick={handleClick}
      className={`wishlist-button ${size} ${inWishlist ? 'active' : ''}`}
      aria-label={inWishlist ? 'Remove from wishlist' : 'Add to wishlist'}
      title={inWishlist ? 'Remove from wishlist' : 'Add to wishlist'}
    >
      <span className="heart-icon">
        {inWishlist ? '❤️' : '🤍'}
      </span>
      {showText && (
        <span className="wishlist-text">
          {inWishlist ? 'In Wishlist' : 'Add to Wishlist'}
        </span>
      )}
    </button>
  );
};

export default WishlistButton;
