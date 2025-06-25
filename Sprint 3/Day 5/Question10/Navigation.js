import React, { useState, useEffect } from 'react';
import NavItem from './NavItem';
import './Navigation.css';

const Navigation = ({ isDarkTheme, currentPage, setCurrentPage }) => {
  const [activeTab, setActiveTab] = useState(currentPage || 'Home');
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  const navigationItems = ['Home', 'About', 'Services', 'Contact'];

  // Sync activeTab with currentPage prop
  useEffect(() => {
    if (currentPage) {
      setActiveTab(currentPage);
    }
  }, [currentPage]);

  const handleNavClick = (tabName) => {
    setActiveTab(tabName);
    if (setCurrentPage) {
      setCurrentPage(tabName);
    }
    setIsMobileMenuOpen(false); // Close mobile menu when item is clicked
  };

  const toggleMobileMenu = () => {
    setIsMobileMenuOpen(!isMobileMenuOpen);
  };

  const navBarStyles = {
    backgroundColor: isDarkTheme ? '#2c3e50' : '#ffffff',
    borderBottom: isDarkTheme ? '2px solid #34495e' : '2px solid #ecf0f1',
    boxShadow: isDarkTheme 
      ? '0 4px 12px rgba(0, 0, 0, 0.3)' 
      : '0 4px 12px rgba(0, 0, 0, 0.1)',
    transition: 'all 0.3s ease'
  };

  const logoStyles = {
    fontSize: '1.8rem',
    fontWeight: 'bold',
    background: 'linear-gradient(45deg, #3498db, #e74c3c)',
    WebkitBackgroundClip: 'text',
    WebkitTextFillColor: 'transparent',
    backgroundClip: 'text',
    margin: 0
  };

  const hamburgerStyles = {
    display: 'none',
    flexDirection: 'column',
    cursor: 'pointer',
    padding: '8px',
    borderRadius: '6px',
    transition: 'background-color 0.3s ease',
    backgroundColor: 'transparent',
    border: 'none'
  };

  const hamburgerLineStyles = {
    width: '25px',
    height: '3px',
    backgroundColor: isDarkTheme ? '#ecf0f1' : '#2c3e50',
    margin: '2px 0',
    borderRadius: '2px',
    transition: 'all 0.3s ease'
  };

  return (
    <nav className="navigation-bar" style={navBarStyles}>
      <div className="nav-container">
        {/* Logo */}
        <div className="nav-logo">
          <h2 style={logoStyles}>🚀 ReactApp</h2>
        </div>

        {/* Desktop Navigation Items */}
        <div className={`nav-items ${isMobileMenuOpen ? 'nav-items-mobile-open' : ''}`}>
          {navigationItems.map((item) => (
            <NavItem
              key={item}
              label={item}
              isActive={activeTab === item}
              onClick={handleNavClick}
              isDarkTheme={isDarkTheme}
            />
          ))}
        </div>

        {/* Mobile Hamburger Menu */}
        <button 
          className="hamburger-menu"
          style={hamburgerStyles}
          onClick={toggleMobileMenu}
        >
          <div style={{
            ...hamburgerLineStyles,
            transform: isMobileMenuOpen ? 'rotate(45deg) translate(5px, 5px)' : 'none'
          }}></div>
          <div style={{
            ...hamburgerLineStyles,
            opacity: isMobileMenuOpen ? '0' : '1'
          }}></div>
          <div style={{
            ...hamburgerLineStyles,
            transform: isMobileMenuOpen ? 'rotate(-45deg) translate(7px, -6px)' : 'none'
          }}></div>
        </button>
      </div>

      {/* Current Page Indicator */}
      <div className="current-page-indicator">
        <span style={{
          color: isDarkTheme ? '#bdc3c7' : '#7f8c8d',
          fontSize: '0.9rem',
          padding: '8px 20px',
          backgroundColor: isDarkTheme ? '#34495e' : '#f8f9fa',
          borderRadius: '0 0 8px 8px',
          display: 'inline-block'
        }}>
          Current Page: <strong style={{ color: isDarkTheme ? '#3498db' : '#e74c3c' }}>
            {activeTab}
          </strong>
        </span>
      </div>
    </nav>
  );
};

export default Navigation;
