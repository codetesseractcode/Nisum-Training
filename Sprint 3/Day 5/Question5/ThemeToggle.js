import React from 'react';
import './ThemeToggle.css';

const ThemeToggle = ({ isDarkTheme, toggleTheme }) => {
  const themeStyles = {
    backgroundColor: isDarkTheme ? '#34495e' : '#ecf0f1',
    color: isDarkTheme ? '#ecf0f1' : '#2c3e50',
    padding: '20px',
    borderRadius: '10px',
    margin: '20px 0',
    transition: 'all 0.3s ease',
    textAlign: 'center',
    border: isDarkTheme ? '2px solid #5d6d7e' : '2px solid #bdc3c7'
  };

  const buttonStyles = {
    backgroundColor: isDarkTheme ? '#e74c3c' : '#3498db',
    color: 'white',
    border: 'none',
    padding: '12px 24px',
    borderRadius: '8px',
    fontSize: '16px',
    fontWeight: 'bold',
    cursor: 'pointer',
    transition: 'all 0.3s ease',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)'
  };
  return (
    <div style={themeStyles} className="theme-toggle-container">
      <h3>🌓 Global Theme Toggle</h3>
      <p>Current Theme: <strong>{isDarkTheme ? 'Dark' : 'Light'}</strong></p>
      <button 
        style={buttonStyles}
        onClick={toggleTheme}
        className="theme-button"
      >
        Switch to {isDarkTheme ? 'Light' : 'Dark'} Theme
      </button>
      <div style={{ marginTop: '15px', fontSize: '14px', opacity: 0.8 }}>
        {isDarkTheme ? '🌙 Dark Mode Active - Affects Entire Website' : '☀️ Light Mode Active - Affects Entire Website'}
      </div>
    </div>
  );
};

export default ThemeToggle;
