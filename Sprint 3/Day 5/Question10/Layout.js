import React from 'react';
import Navigation from './Navigation';
import './Layout.css';

const Layout = ({ children, isDarkTheme, toggleTheme, currentPage, setCurrentPage }) => {
  // Layout container styles
  const layoutStyles = {
    backgroundColor: isDarkTheme ? '#1a1a1a' : '#ffffff',
    color: isDarkTheme ? '#ecf0f1' : '#2c3e50',
    transition: 'all 0.3s ease'
  };

  const headerStyles = {
    backgroundColor: isDarkTheme ? '#2c3e50' : '#f8f9fa',
    borderBottom: isDarkTheme ? '2px solid #34495e' : '2px solid #e9ecef',
    transition: 'all 0.3s ease'
  };

  const footerStyles = {
    backgroundColor: isDarkTheme ? '#2c3e50' : '#f8f9fa',
    borderTop: isDarkTheme ? '2px solid #34495e' : '2px solid #e9ecef',
    color: isDarkTheme ? '#bdc3c7' : '#7f8c8d',
    transition: 'all 0.3s ease'
  };

  return (
    <div className="layout-container" style={layoutStyles}>      {/* Header Section */}
      <header className="layout-header" style={headerStyles}>
        <Navigation 
          isDarkTheme={isDarkTheme} 
          currentPage={currentPage}
          setCurrentPage={setCurrentPage}
        />
        <div className="header-content">
          <div className="header-info">
            <h1 className="site-title">
              🚀 React Component Library
            </h1>
            <p className="site-subtitle">
              Building amazing user interfaces with reusable components
            </p>
          </div>
          
          <div className="header-actions">
            <button
              className="theme-toggle-btn"
              onClick={toggleTheme}
              style={{
                backgroundColor: isDarkTheme ? '#e74c3c' : '#3498db',
                color: 'white',
                border: 'none',
                padding: '10px 20px',
                borderRadius: '25px',
                cursor: 'pointer',
                fontSize: '14px',
                fontWeight: '600',
                transition: 'all 0.3s ease',
                boxShadow: '0 2px 8px rgba(0, 0, 0, 0.2)'
              }}
            >
              {isDarkTheme ? '☀️ Light Mode' : '🌙 Dark Mode'}
            </button>
          </div>
        </div>
      </header>

      {/* Main Content Section */}
      <main className="layout-main">
        <div className="main-content">
          {children}
        </div>
      </main>

      {/* Footer Section */}
      <footer className="layout-footer" style={footerStyles}>
        <div className="footer-content">
          <div className="footer-section">
            <h4>Quick Links</h4>
            <ul>
              <li><a href="#home" style={{ color: isDarkTheme ? '#3498db' : '#2980b9' }}>Home</a></li>
              <li><a href="#about" style={{ color: isDarkTheme ? '#3498db' : '#2980b9' }}>About</a></li>
              <li><a href="#services" style={{ color: isDarkTheme ? '#3498db' : '#2980b9' }}>Services</a></li>
              <li><a href="#contact" style={{ color: isDarkTheme ? '#3498db' : '#2980b9' }}>Contact</a></li>
            </ul>
          </div>
          
          <div className="footer-section">
            <h4>Technologies</h4>
            <ul>
              <li>React.js</li>
              <li>CSS Grid</li>
              <li>Flexbox</li>
              <li>JavaScript ES6+</li>
            </ul>
          </div>
          
          <div className="footer-section">
            <h4>Connect</h4>
            <div className="social-links">
              <span style={{ margin: '0 10px', fontSize: '20px', cursor: 'pointer' }}>📧</span>
              <span style={{ margin: '0 10px', fontSize: '20px', cursor: 'pointer' }}>💼</span>
              <span style={{ margin: '0 10px', fontSize: '20px', cursor: 'pointer' }}>🐙</span>
              <span style={{ margin: '0 10px', fontSize: '20px', cursor: 'pointer' }}>📱</span>
            </div>
          </div>
        </div>
        
        <div className="footer-bottom">
          <p>&copy; 2025 React Component Library. Built with ❤️ using React & CSS Grid</p>
          <p>
            Current Theme: <strong>{isDarkTheme ? 'Dark' : 'Light'}</strong> | 
            Last Updated: {new Date().toLocaleDateString()}
          </p>
        </div>
      </footer>
    </div>
  );
};

export default Layout;
