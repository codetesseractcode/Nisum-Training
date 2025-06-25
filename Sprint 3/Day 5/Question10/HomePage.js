import React from 'react';

const HomePage = ({ isDarkTheme }) => {
  const sectionStyles = {
    backgroundColor: isDarkTheme ? '#34495e' : '#f8f9fa',
    padding: '30px',
    borderRadius: '12px',
    margin: '20px 0',
    border: isDarkTheme ? '2px solid #5d6d7e' : '2px solid #e9ecef',
    transition: 'all 0.3s ease'
  };

  const titleStyles = {
    color: isDarkTheme ? '#3498db' : '#2c3e50',
    textAlign: 'center',
    marginBottom: '20px',
    fontSize: '2.2rem',
    fontWeight: '700'
  };

  const cardStyles = {
    backgroundColor: isDarkTheme ? '#2c3e50' : '#ffffff',
    padding: '25px',
    borderRadius: '10px',
    margin: '15px 0',
    boxShadow: '0 4px 12px rgba(0, 0, 0, 0.1)',
    border: isDarkTheme ? '1px solid #5d6d7e' : '1px solid #e9ecef',
    transition: 'all 0.3s ease'
  };

  return (
    <div className="home-page">
      <h1 style={titleStyles}>🏠 Welcome to the Home Page</h1>
      
      <div style={sectionStyles}>
        <h2 style={{ color: isDarkTheme ? '#e74c3c' : '#3498db', textAlign: 'center' }}>
          🚀 React Component Showcase
        </h2>
        <p style={{ fontSize: '1.1rem', textAlign: 'center', lineHeight: '1.6' }}>
          This is the Home page wrapped in our reusable Layout component. 
          The Layout uses CSS Grid to structure the page with Header, Main content, and Footer sections.
        </p>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(300px, 1fr))', gap: '20px' }}>
        <div style={cardStyles}>
          <h3 style={{ color: isDarkTheme ? '#f39c12' : '#e67e22', margin: '0 0 15px 0' }}>
            🎯 Features
          </h3>
          <ul style={{ lineHeight: '1.8', paddingLeft: '20px' }}>
            <li>Responsive Layout with CSS Grid</li>
            <li>Dark/Light Theme Support</li>
            <li>Reusable Components</li>
            <li>Navigation with Active States</li>
            <li>Mobile-Friendly Design</li>
          </ul>
        </div>

        <div style={cardStyles}>
          <h3 style={{ color: isDarkTheme ? '#2ecc71' : '#27ae60', margin: '0 0 15px 0' }}>
            🛠️ Technologies
          </h3>
          <ul style={{ lineHeight: '1.8', paddingLeft: '20px' }}>
            <li>React.js with Hooks</li>
            <li>CSS Grid & Flexbox</li>
            <li>JavaScript ES6+</li>
            <li>Responsive Design</li>
            <li>Component Architecture</li>
          </ul>
        </div>

        <div style={cardStyles}>
          <h3 style={{ color: isDarkTheme ? '#9b59b6' : '#8e44ad', margin: '0 0 15px 0' }}>
            📱 Components
          </h3>
          <ul style={{ lineHeight: '1.8', paddingLeft: '20px' }}>
            <li>Navigation Bar</li>
            <li>Theme Toggle</li>
            <li>User Profile Cards</li>
            <li>Todo Lists</li>
            <li>Product Cards</li>
          </ul>
        </div>
      </div>

      <div style={sectionStyles}>
        <h2 style={{ color: isDarkTheme ? '#e74c3c' : '#3498db', textAlign: 'center' }}>
          💡 About This Layout
        </h2>
        <p style={{ fontSize: '1rem', lineHeight: '1.6', textAlign: 'center' }}>
          The Layout component demonstrates the power of CSS Grid for creating 
          structured, responsive web layouts. It includes a sticky header with navigation, 
          a flexible main content area (where this content appears), and a comprehensive footer.
        </p>
      </div>
    </div>
  );
};

export default HomePage;
