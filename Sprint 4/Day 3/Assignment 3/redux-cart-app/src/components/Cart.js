import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import {
  removeFromCart,
  incrementQuantity,
  decrementQuantity,
  clearCart,
  closeCart
} from '../store/slices/cartSlice';
import {
  selectCartItems,
  selectCartTotalQuantity,
  selectCartTotalAmount,
  selectIsCartOpen
} from '../store/slices/cartSlice';
import './Cart.css';

// Cart Item Component - following Single Responsibility Principle
function CartItem({ item }) {
  const dispatch = useDispatch();

  const handleRemove = () => {
    dispatch(removeFromCart(item.id));
  };

  const handleIncrement = () => {
    dispatch(incrementQuantity(item.id));
  };

  const handleDecrement = () => {
    dispatch(decrementQuantity(item.id));
  };

  return (
    <div className="cart-item">
      <div className="item-image">
        <span className="item-emoji">{item.image}</span>
      </div>
      
      <div className="item-details">
        <h4 className="item-name">{item.name}</h4>
        <p className="item-price">${item.price}</p>
      </div>
      
      <div className="item-controls">
        <div className="quantity-controls">
          <button 
            className="quantity-button"
            onClick={handleDecrement}
            disabled={item.quantity <= 1}
          >
            -
          </button>
          <span className="quantity">{item.quantity}</span>
          <button 
            className="quantity-button"
            onClick={handleIncrement}
          >
            +
          </button>
        </div>
        
        <div className="item-total">
          ${(item.price * item.quantity).toFixed(2)}
        </div>
        
        <button 
          className="remove-button"
          onClick={handleRemove}
        >
          🗑️
        </button>
      </div>
    </div>
  );
}

// Cart Component - following Single Responsibility Principle
export function Cart() {
  const dispatch = useDispatch();
  const cartItems = useSelector(selectCartItems);
  const totalQuantity = useSelector(selectCartTotalQuantity);
  const totalAmount = useSelector(selectCartTotalAmount);
  const isCartOpen = useSelector(selectIsCartOpen);

  const handleClearCart = () => {
    dispatch(clearCart());
  };

  const handleCloseCart = () => {
    dispatch(closeCart());
  };

  if (!isCartOpen) return null;

  return (
    <div className="cart-overlay">
      <div className="cart-container">
        <div className="cart-header">
          <h2>Shopping Cart</h2>
          <button 
            className="close-button"
            onClick={handleCloseCart}
          >
            ✕
          </button>
        </div>
        
        <div className="cart-content">
          {cartItems.length === 0 ? (
            <div className="empty-cart">
              <div className="empty-cart-icon">🛒</div>
              <h3>Your cart is empty</h3>
              <p>Add some products to get started!</p>
            </div>
          ) : (
            <>
              <div className="cart-items">
                {cartItems.map(item => (
                  <CartItem key={item.id} item={item} />
                ))}
              </div>
              
              <div className="cart-summary">
                <div className="summary-line">
                  <span>Total Items:</span>
                  <span className="summary-value">{totalQuantity}</span>
                </div>
                <div className="summary-line">
                  <span>Subtotal:</span>
                  <span className="summary-value">${totalAmount.toFixed(2)}</span>
                </div>
                <div className="summary-line">
                  <span>Shipping:</span>
                  <span className="summary-value">Free</span>
                </div>
                <div className="summary-line total-line">
                  <span>Total:</span>
                  <span className="summary-value">${totalAmount.toFixed(2)}</span>
                </div>
              </div>
              
              <div className="cart-actions">
                <button 
                  className="clear-cart-button"
                  onClick={handleClearCart}
                >
                  Clear Cart
                </button>
                <button className="checkout-button">
                  Proceed to Checkout
                </button>
              </div>
            </>
          )}
        </div>
      </div>
    </div>
  );
}
