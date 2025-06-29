import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useWishlistContext } from '../contexts/WishlistContext';
import { getCategoryFallbackImage } from '../utils/fallbackImages';
import WishlistButton from './WishlistButton';
import Breadcrumb from './Breadcrumb';
import './WishlistPage.css';

const WishlistPage = () => {
  const { wishlistItems, clearWishlist } = useWishlistContext();
  const navigate = useNavigate();
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Fetch all products to get complete product information
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        const response = await fetch('/products.json');
        if (!response.ok) {
          throw new Error('Failed to fetch products');
        }
        const data = await response.json();
        setProducts(data.products);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  // Get full product details for wishlist items
  const wishlistProducts = products.filter(product => 
    wishlistItems.some(item => item.id === product.id)
  );

  const handleClearWishlist = () => {
    if (window.confirm('Are you sure you want to clear your entire wishlist?')) {
      clearWishlist();
    }
  };

  const handleAddToCart = (product) => {
    alert(`Added "${product.name}" to cart!`);
  };

  if (loading) {
    return (
      <div className="wishlist-container">
        <div className="loading">
          <div className="loading-spinner"></div>
          <p>Loading your wishlist...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="wishlist-container">
        <div className="error">
          <h2>Error Loading Wishlist</h2>
          <p>{error}</p>
          <button onClick={() => window.location.reload()}>Retry</button>
        </div>
      </div>
    );
  }

  return (
    <div className="wishlist-container">
      <Breadcrumb 
        items={[
          { label: 'Products', path: '/' },
          { label: 'Wishlist', path: '/wishlist' }
        ]}
      />

      <div className="wishlist-header">
        <h1>My Wishlist</h1>
        <p>
          {wishlistItems.length === 0 
            ? 'Your wishlist is empty' 
            : `${wishlistItems.length} item${wishlistItems.length !== 1 ? 's' : ''} in your wishlist`
          }
        </p>
      </div>

      {wishlistItems.length === 0 ? (
        <div className="empty-wishlist">
          <div className="empty-icon">💔</div>
          <h3>Your wishlist is empty</h3>
          <p>Start browsing and add products you love to your wishlist!</p>
          <Link to="/" className="browse-products-btn">
            Browse Products
          </Link>
        </div>
      ) : (
        <>
          <div className="wishlist-actions">
            <button 
              onClick={handleClearWishlist}
              className="clear-wishlist-btn"
            >
              Clear All
            </button>
          </div>

          <div className="wishlist-grid">
            {wishlistProducts.map(product => (
              <WishlistCard 
                key={product.id}
                product={product}
                onAddToCart={handleAddToCart}
                onNavigate={() => navigate(`/products/${product.id}`)}
              />
            ))}
          </div>
        </>
      )}
    </div>
  );
};

// Individual wishlist item card component
const WishlistCard = ({ product, onAddToCart, onNavigate }) => {
  const [imageError, setImageError] = useState(false);
  const [imageLoading, setImageLoading] = useState(true);

  const handleImageError = () => {
    setImageError(true);
    setImageLoading(false);
  };

  const handleImageLoad = () => {
    setImageLoading(false);
  };

  const handleCardClick = (e) => {
    if (!e.target.closest('button')) {
      onNavigate();
    }
  };

  const fallbackImage = getCategoryFallbackImage(product.category);

  return (
    <div className="wishlist-card" onClick={handleCardClick}>
      <div className="wishlist-card-image-container">
        {imageLoading && (
          <div className="image-placeholder">
            <div className="image-loading-spinner"></div>
          </div>
        )}
        <img
          src={imageError ? fallbackImage : product.image}
          alt={product.name}
          className={`wishlist-card-image ${imageLoading ? 'loading' : ''}`}
          onError={handleImageError}
          onLoad={handleImageLoad}
          style={{ display: imageLoading ? 'none' : 'block' }}
        />
        <div className="wishlist-button-container">
          <WishlistButton product={product} size="medium" />
        </div>
        <div className="product-category-badge">
          {product.category}
        </div>
      </div>

      <div className="wishlist-card-content">
        <h3 className="wishlist-card-title">{product.name}</h3>
        <p className="wishlist-card-description">{product.description}</p>
        
        <div className="wishlist-card-footer">
          <div className="wishlist-card-price">
            ${product.price.toLocaleString()}
          </div>
          <div className="wishlist-card-actions">
            <button
              onClick={() => onAddToCart(product)}
              className="add-to-cart-btn"
            >
              Add to Cart
            </button>
            <button
              onClick={onNavigate}
              className="view-details-btn"
            >
              View Details
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default WishlistPage;
