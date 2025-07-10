import React, { useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { loginUser, clearError } from '../store/slices/userSlice';
import {
  selectIsLoading,
  selectError
} from '../store/slices/userSlice';
import './Login.css';

// Login component - following Single Responsibility Principle
export function Login() {
  const dispatch = useDispatch();
  const isLoading = useSelector(selectIsLoading);
  const error = useSelector(selectError);
  
  const [formData, setFormData] = useState({
    email: '',
    password: ''
  });

  const [showCredentials, setShowCredentials] = useState(false);

  const mockCredentials = [
    { email: 'admin@example.com', password: 'admin123', role: 'Admin' },
    { email: 'user@example.com', password: 'user123', role: 'Customer' },
    { email: 'jane@example.com', password: 'jane123', role: 'Customer' }
  ];

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
    
    // Clear error when user starts typing
    if (error) {
      dispatch(clearError());
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    dispatch(loginUser(formData));
  };

  const fillCredentials = (credentials) => {
    setFormData({
      email: credentials.email,
      password: credentials.password
    });
  };

  return (
    <div className="login-container">
      <div className="login-card">
        <div className="login-header">
          <h2>Welcome to Redux Store</h2>
          <p>Please log in to start shopping</p>
        </div>

        <form onSubmit={handleSubmit} className="login-form">
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
              placeholder="Enter your email"
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
              placeholder="Enter your password"
            />
          </div>

          {error && (
            <div className="error-message">
              {error}
            </div>
          )}

          <button 
            type="submit" 
            className="login-button"
            disabled={isLoading}
          >
            {isLoading ? 'Logging in...' : 'Login'}
          </button>
        </form>

        <div className="demo-section">
          <button 
            className="demo-toggle"
            onClick={() => setShowCredentials(!showCredentials)}
          >
            {showCredentials ? 'Hide' : 'Show'} Demo Credentials
          </button>
          
          {showCredentials && (
            <div className="demo-credentials">
              <h4>Demo Accounts:</h4>
              {mockCredentials.map((cred, index) => (
                <div key={index} className="credential-item">
                  <div className="credential-info">
                    <strong>{cred.role}:</strong> {cred.email}
                  </div>
                  <button 
                    className="use-credentials"
                    onClick={() => fillCredentials(cred)}
                  >
                    Use This Account
                  </button>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
