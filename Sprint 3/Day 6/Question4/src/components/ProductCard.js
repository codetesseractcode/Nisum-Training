import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { getCategoryFallbackImage } from '../utils/fallbackImages';
import './ProductCard.css';

const ProductCard = ({ product }) => {
  const { id, name, category, price, description, image } = product;
  const navigate = useNavigate();
  const [imageError, setImageError] = useState(false);
  const [imageLoading, setImageLoading] = useState(true);

  const handleAddToCart = () => {
    // Placeholder for add to cart functionality
    alert(`Added "${name}" to cart!`);
  };

  const handleViewDetails = () => {
    navigate(`/products/${id}`);
  };

  const handleCardClick = (e) => {
    // Navigate to product detail if clicking on the card but not on buttons
    if (!e.target.closest('button')) {
      handleViewDetails();
    }
  };

  const handleImageError = () => {
    setImageError(true);
    setImageLoading(false);
  };

  const handleImageLoad = () => {
    setImageLoading(false);
  };

  // Fallback image URL - use category-specific fallback
  const fallbackImage = getCategoryFallbackImage(category);

  return (
    <div className="product-card" onClick={handleCardClick}>
      <div className="product-image-container">
        {imageLoading && (
          <div className="image-placeholder">
            <div className="image-loading-spinner"></div>
          </div>
        )}
        <img
          src={imageError ? fallbackImage : image}
          alt={name}
          className={`product-image ${imageLoading ? 'loading' : ''}`}
          loading="lazy"
          onError={handleImageError}
          onLoad={handleImageLoad}
          style={{ display: imageLoading ? 'none' : 'block' }}
        />
        <div className="product-category-badge">
          {category}
        </div>
      </div>
      
      <div className="product-content">
        <h3 className="product-name">{name}</h3>
        <p className="product-description">{description}</p>
        
        <div className="product-footer">
          <div className="product-price">
            ${price.toLocaleString()}
          </div>
          <div className="product-actions">
            <button
              onClick={handleViewDetails}
              className="view-details-btn"
              aria-label={`View details for ${name}`}
            >
              View Details
            </button>
            <button
              onClick={handleAddToCart}
              className="add-to-cart-btn"
              aria-label={`Add ${name} to cart`}
            >
              Add to Cart
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductCard;
