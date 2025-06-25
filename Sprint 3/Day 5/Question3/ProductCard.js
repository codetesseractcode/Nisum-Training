import React from 'react';
import './ProductCard.css';

const ProductCard = ({ title, price, description }) => {
  return (
    <div className="product-card">
      <h2>{title}</h2>
      <p>Price: ${price}</p>
      <p>{description}</p>
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
