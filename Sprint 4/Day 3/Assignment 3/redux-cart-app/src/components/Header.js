import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { logout } from '../store/slices/userSlice';
import { toggleCart } from '../store/slices/cartSlice';
import {
  selectCurrentUser,
  selectIsLoggedIn
} from '../store/slices/userSlice';
import {
  selectCartTotalQuantity,
  selectCartTotalAmount
} from '../store/slices/cartSlice';
import './Header.css';

// Header component - following Single Responsibility Principle
export function Header() {
  const dispatch = useDispatch();
  const currentUser = useSelector(selectCurrentUser);
  const isLoggedIn = useSelector(selectIsLoggedIn);
  const cartQuantity = useSelector(selectCartTotalQuantity);
  const cartTotal = useSelector(selectCartTotalAmount);

  const handleLogout = () => {
    dispatch(logout());
  };

  const handleCartToggle = () => {
    dispatch(toggleCart());
  };

  return (
    <header className="header">
      <div className="header-content">
        <div className="header-left">
          <h1 className="logo">🛒 Redux Store</h1>
          <p className="tagline">Your favorite e-commerce destination</p>
        </div>
        
        <div className="header-right">
          {isLoggedIn ? (
            <div className="user-section">
              <div className="user-info">
                <span className="user-avatar">{currentUser.avatar}</span>
                <div className="user-details">
                  <span className="user-name">{currentUser.name}</span>
                  <span className="user-role">{currentUser.role}</span>
                </div>
              </div>
              
              <button 
                className="cart-button"
                onClick={handleCartToggle}
              >
                <span className="cart-icon">🛒</span>
                <div className="cart-info">
                  <span className="cart-count">{cartQuantity}</span>
                  <span className="cart-total">${cartTotal.toFixed(2)}</span>
                </div>
              </button>
              
              <button 
                className="logout-button"
                onClick={handleLogout}
              >
                Logout
              </button>
            </div>
          ) : (
            <div className="auth-section">
              <p className="login-prompt">Please log in to shop</p>
            </div>
          )}
        </div>
      </div>
    </header>
  );
}
