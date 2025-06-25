import React from 'react';
import './ProductCard.css';

const ProductCard = ({ title, price, description }) => {
  // Inline styles object
  const cardStyles = {
    border: '1px solid black',
    borderRadius: '10px',
    padding: '20px',
    margin: '15px',
    backgroundColor: '#f9f9f9',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
    maxWidth: '350px',
    fontFamily: 'Arial, sans-serif'
  };

  const titleStyles = {
    color: '#2c3e50',
    fontSize: '1.6rem',
    fontWeight: 'bold',
    marginBottom: '10px',
    textAlign: 'center'
  };

  const priceStyles = {
    color: '#e74c3c',
    fontSize: '1.2rem',
    fontWeight: '600',
    margin: '8px 0'
  };

  const descriptionStyles = {
    color: '#555',
    fontSize: '0.95rem',
    lineHeight: '1.6',
    fontStyle: 'italic'
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
