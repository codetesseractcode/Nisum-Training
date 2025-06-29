import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useWishlistContext } from '../contexts/WishlistContext';
import './Navigation.css';

const Navigation = () => {
  const { wishlistCount } = useWishlistContext();
  const location = useLocation();

  return (
    <nav className="navigation">
      <div className="nav-container">
        <Link to="/" className="nav-logo">
          🛍️ ShopApp
        </Link>
        
        <div className="nav-links">
          <Link 
            to="/" 
            className={`nav-link ${location.pathname === '/' ? 'active' : ''}`}
          >
            Products
          </Link>
          
          <Link 
            to="/wishlist" 
            className={`nav-link wishlist-nav-link ${location.pathname === '/wishlist' ? 'active' : ''}`}
          >
            <span className="wishlist-icon">❤️</span>
            <span className="nav-text">Wishlist</span>
            {wishlistCount > 0 && (
              <span className="wishlist-badge">{wishlistCount}</span>
            )}
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default Navigation;
