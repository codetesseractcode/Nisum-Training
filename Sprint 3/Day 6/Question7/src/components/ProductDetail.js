import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getCategoryFallbackImage } from '../utils/fallbackImages';
import Breadcrumb from './Breadcrumb';
import WishlistButton from './WishlistButton';
import './ProductDetail.css';

const ProductDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [imageError, setImageError] = useState(false);
  const [imageLoading, setImageLoading] = useState(true);
  const [quantity, setQuantity] = useState(1);

  useEffect(() => {
    const fetchProduct = async () => {
      try {
        setLoading(true);
        const response = await fetch('/products.json');
        if (!response.ok) {
          throw new Error('Failed to fetch product data');
        }
        const data = await response.json();
        const foundProduct = data.products.find(p => p.id === parseInt(id));
        
        if (!foundProduct) {
          throw new Error('Product not found');
        }
        
        setProduct(foundProduct);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchProduct();
  }, [id]);

  const handleGoBack = () => {
    navigate(-1);
  };

  const handleImageError = () => {
    setImageError(true);
    setImageLoading(false);
  };

  const handleImageLoad = () => {
    setImageLoading(false);
  };

  const handleAddToCart = () => {
    alert(`Added ${quantity} ${product.name}(s) to cart!`);
  };

  const handleQuantityChange = (e) => {
    const value = parseInt(e.target.value);
    if (value >= 1 && value <= product.stock) {
      setQuantity(value);
    }
  };

  if (loading) {
    return (
      <div className="product-detail-container">
        <div className="loading">
          <div className="loading-spinner"></div>
          <p>Loading product details...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="product-detail-container">
        <div className="error">
          <h2>Product Not Found</h2>
          <p>{error}</p>
          <button onClick={handleGoBack} className="go-back-btn">
            Go Back
          </button>
        </div>
      </div>
    );
  }

  if (!product) {
    return null;
  }

  const fallbackImage = getCategoryFallbackImage(product.category);
  const isLowStock = product.stock <= 5;
  const isOutOfStock = product.stock === 0;

  return (
    <div className="product-detail-container">
      <Breadcrumb 
        items={[
          { label: 'Products', path: '/' },
          { label: product.category, path: `/?category=${product.category}` },
          { label: product.name, path: `/products/${product.id}` }
        ]}
      />
      
      <button onClick={handleGoBack} className="go-back-btn">
        ← Go Back
      </button>

      <div className="product-detail-content">
        <div className="product-image-section">
          {imageLoading && (
            <div className="image-placeholder">
              <div className="image-loading-spinner"></div>
            </div>
          )}
          <img
            src={imageError ? fallbackImage : product.image}
            alt={product.name}
            className={`product-detail-image ${imageLoading ? 'loading' : ''}`}
            onError={handleImageError}
            onLoad={handleImageLoad}
            style={{ display: imageLoading ? 'none' : 'block' }}
          />
          <div className="product-category-badge">
            {product.category}
          </div>
        </div>          <div className="product-info-section">
            <div className="product-title-row">
              <h1 className="product-title">{product.name}</h1>
              <WishlistButton product={product} size="large" showText={true} />
            </div>
          
          <div className="price-stock-section">
            <div className="product-price">${product.price.toLocaleString()}</div>
            <div className={`stock-info ${isLowStock ? 'low-stock' : ''} ${isOutOfStock ? 'out-of-stock' : ''}`}>
              {isOutOfStock ? (
                <span className="stock-status">Out of Stock</span>
              ) : isLowStock ? (
                <span className="stock-status">Only {product.stock} left!</span>
              ) : (
                <span className="stock-status">{product.stock} in stock</span>
              )}
            </div>
          </div>

          <div className="product-description">
            <h3>Description</h3>
            <p>{product.detailedDescription || product.description}</p>
          </div>

          {product.specifications && (
            <div className="product-specifications">
              <h3>Specifications</h3>
              <div className="specs-grid">
                {Object.entries(product.specifications).map(([key, value]) => (
                  <div key={key} className="spec-item">
                    <span className="spec-label">{key}:</span>
                    <span className="spec-value">{value}</span>
                  </div>
                ))}
              </div>
            </div>
          )}

          <div className="purchase-section">
            <div className="quantity-selector">
              <label htmlFor="quantity">Quantity:</label>
              <input
                id="quantity"
                type="number"
                min="1"
                max={product.stock}
                value={quantity}
                onChange={handleQuantityChange}
                disabled={isOutOfStock}
              />
            </div>
            
            <button
              onClick={handleAddToCart}
              className={`add-to-cart-btn ${isOutOfStock ? 'disabled' : ''}`}
              disabled={isOutOfStock}
            >
              {isOutOfStock ? 'Out of Stock' : 'Add to Cart'}
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail;
