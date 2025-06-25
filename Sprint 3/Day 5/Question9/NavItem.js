import React from 'react';

const NavItem = ({ label, isActive, onClick, isDarkTheme }) => {
  const navItemStyles = {
    padding: '12px 20px',
    margin: '0 5px',
    borderRadius: '8px',
    cursor: 'pointer',
    transition: 'all 0.3s ease',
    fontSize: '1rem',
    fontWeight: '500',
    textDecoration: 'none',
    border: 'none',
    background: 'none',
    color: isActive 
      ? '#ffffff' 
      : isDarkTheme 
        ? '#bdc3c7' 
        : '#2c3e50',
    backgroundColor: isActive 
      ? (isDarkTheme ? '#e74c3c' : '#3498db')
      : 'transparent',
    boxShadow: isActive 
      ? '0 4px 12px rgba(52, 152, 219, 0.3)' 
      : 'none',
    position: 'relative',
    overflow: 'hidden'
  };

  const hoverStyles = {
    transform: 'translateY(-2px)',
    backgroundColor: isActive 
      ? (isDarkTheme ? '#c0392b' : '#2980b9')
      : (isDarkTheme ? '#34495e' : '#ecf0f1'),
    boxShadow: isActive 
      ? '0 6px 16px rgba(52, 152, 219, 0.4)' 
      : '0 2px 8px rgba(0, 0, 0, 0.1)'
  };

  const handleMouseEnter = (e) => {
    Object.assign(e.target.style, hoverStyles);
  };

  const handleMouseLeave = (e) => {
    Object.assign(e.target.style, navItemStyles);
  };

  return (
    <button
      style={navItemStyles}
      onClick={() => onClick(label)}
      onMouseEnter={handleMouseEnter}
      onMouseLeave={handleMouseLeave}
      className="nav-item"
    >
      {label}
      {isActive && (
        <span 
          style={{
            position: 'absolute',
            bottom: '0',
            left: '0',
            right: '0',
            height: '3px',
            background: 'linear-gradient(45deg, #f39c12, #e74c3c)',
            borderRadius: '2px 2px 0 0'
          }}
        />
      )}
    </button>
  );
};

export default NavItem;
