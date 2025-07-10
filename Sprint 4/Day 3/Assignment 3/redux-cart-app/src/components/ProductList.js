import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { addToCart } from '../store/slices/cartSlice';
import {
  filterByCategory,
  searchProducts,
  sortProducts
} from '../store/slices/productsSlice';
import {
  selectFilteredProducts,
  selectCategories,
  selectSelectedCategory,
  selectSearchTerm,
  selectSortBy
} from '../store/slices/productsSlice';
import './ProductList.css';

// Product Card Component - following Single Responsibility Principle
function ProductCard({ product }) {
  const dispatch = useDispatch();

  const handleAddToCart = () => {
    dispatch(addToCart(product));
  };

  const renderStars = (rating) => {
    const stars = [];
    const fullStars = Math.floor(rating);
    const hasHalfStar = rating % 1 !== 0;
    
    for (let i = 0; i < fullStars; i++) {
      stars.push('⭐');
    }
    if (hasHalfStar) {
      stars.push('⭐');
    }
    
    return stars.join('');
  };

  return (
    <div className="product-card">
      <div className="product-image">
        <span className="product-emoji">{product.image}</span>
        <div className="product-badge">
          {product.stock < 10 ? 'Low Stock' : 'In Stock'}
        </div>
      </div>
      
      <div className="product-info">
        <h3 className="product-name">{product.name}</h3>
        <p className="product-description">{product.description}</p>
        
        <div className="product-meta">
          <span className="product-category">{product.category}</span>
          <div className="product-rating">
            <span className="stars">{renderStars(product.rating)}</span>
            <span className="rating-value">({product.rating})</span>
          </div>
        </div>
        
        <div className="product-footer">
          <div className="product-price">
            <span className="price">${product.price}</span>
            <span className="stock-info">{product.stock} left</span>
          </div>
          
          <button 
            className="add-to-cart-button"
            onClick={handleAddToCart}
            disabled={product.stock === 0}
          >
            {product.stock === 0 ? 'Out of Stock' : 'Add to Cart'}
          </button>
        </div>
      </div>
    </div>
  );
}

// Product List Component - following Single Responsibility Principle
export function ProductList() {
  const dispatch = useDispatch();
  const products = useSelector(selectFilteredProducts);
  const categories = useSelector(selectCategories);
  const selectedCategory = useSelector(selectSelectedCategory);
  const searchTerm = useSelector(selectSearchTerm);
  const sortBy = useSelector(selectSortBy);

  const handleCategoryChange = (category) => {
    dispatch(filterByCategory(category));
  };

  const handleSearchChange = (e) => {
    dispatch(searchProducts(e.target.value));
  };

  const handleSortChange = (e) => {
    dispatch(sortProducts(e.target.value));
  };

  return (
    <div className="product-list-container">
      <div className="product-controls">
        <div className="search-section">
          <input
            type="text"
            placeholder="Search products..."
            value={searchTerm}
            onChange={handleSearchChange}
            className="search-input"
          />
        </div>
        
        <div className="filter-section">
          <div className="category-filters">
            <label>Category:</label>
            <div className="category-buttons">
              {categories.map(category => (
                <button
                  key={category}
                  className={`category-button ${selectedCategory === category ? 'active' : ''}`}
                  onClick={() => handleCategoryChange(category)}
                >
                  {category}
                </button>
              ))}
            </div>
          </div>
          
          <div className="sort-section">
            <label htmlFor="sort">Sort by:</label>
            <select
              id="sort"
              value={sortBy}
              onChange={handleSortChange}
              className="sort-select"
            >
              <option value="name">Name</option>
              <option value="price-low">Price: Low to High</option>
              <option value="price-high">Price: High to Low</option>
              <option value="rating">Rating</option>
            </select>
          </div>
        </div>
      </div>
      
      <div className="products-grid">
        {products.length === 0 ? (
          <div className="no-products">
            <h3>No products found</h3>
            <p>Try adjusting your search or filter criteria</p>
          </div>
        ) : (
          products.map(product => (
            <ProductCard key={product.id} product={product} />
          ))
        )}
      </div>
    </div>
  );
}
