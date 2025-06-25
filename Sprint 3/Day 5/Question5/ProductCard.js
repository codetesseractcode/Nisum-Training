import React from 'react';
import './ProductCard.css';

const ProductCard = ({ title, price, description, isDarkTheme }) => {
  // Inline styles object with theme support
  const cardStyles = {
    border: isDarkTheme ? '1px solid #5d6d7e' : '1px solid black',
    borderRadius: '10px',
    padding: '20px',
    margin: '15px',
    backgroundColor: isDarkTheme ? '#2c3e50' : '#f9f9f9',
    boxShadow: isDarkTheme ? '0 4px 8px rgba(255, 255, 255, 0.1)' : '0 4px 8px rgba(0, 0, 0, 0.1)',
    maxWidth: '350px',
    fontFamily: 'Arial, sans-serif',
    transition: 'all 0.3s ease'
  };
  const titleStyles = {
    color: isDarkTheme ? '#3498db' : '#2c3e50',
    fontSize: '1.6rem',
    fontWeight: 'bold',
    marginBottom: '10px',
    textAlign: 'center',
    transition: 'all 0.3s ease'
  };

  const priceStyles = {
    color: isDarkTheme ? '#e74c3c' : '#e74c3c',
    fontSize: '1.2rem',
    fontWeight: '600',
    margin: '8px 0',
    transition: 'all 0.3s ease'
  };
  const descriptionStyles = {
    color: isDarkTheme ? '#bdc3c7' : '#555',
    fontSize: '0.95rem',
    lineHeight: '1.6',
    fontStyle: 'italic',
    transition: 'all 0.3s ease'
  };

  return (
    <div style={cardStyles} className="product-card-external">
      <h2 style={titleStyles}>{title}</h2>
      <p style={priceStyles}>Price: ${price}</p>
      <p style={descriptionStyles}>{description}</p>
    </div>
  );
};

// Default props
ProductCard.defaultProps = {
  title: 'Product Name',
  price: 0,
  description: 'No description available.'
};

export default ProductCard;
