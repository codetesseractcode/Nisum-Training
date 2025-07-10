import React, { useState } from 'react';

const ProductEdit = () => {
  const [product, setProduct] = useState({
    id: 1,
    name: 'Premium Laptop',
    price: 1299.99,
    description: 'High-performance laptop for professional use',
    category: 'Electronics',
    inStock: true
  });

  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({ ...product });

  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value
    }));
  };

  const handleSave = () => {
    setProduct({ ...formData });
    setIsEditing(false);
    alert('Product updated successfully!');
  };

  const handleCancel = () => {
    setFormData({ ...product });
    setIsEditing(false);
  };

  const productCardStyle = {
    maxWidth: '500px',
    margin: '20px auto',
    padding: '20px',
    border: '1px solid #ddd',
    borderRadius: '4px',
    backgroundColor: '#fff',
    color: '#333'
  };

  const formGroupStyle = {
    marginBottom: '15px'
  };

  const labelStyle = {
    display: 'block',
    marginBottom: '5px',
    fontWeight: 'normal'
  };

  const inputStyle = {
    width: '100%',
    padding: '8px',
    border: '1px solid #ddd',
    borderRadius: '4px',
    fontSize: '14px',
    boxSizing: 'border-box'
  };

  const buttonStyle = {
    padding: '8px 16px',
    margin: '0 5px 0 0',
    border: '1px solid #ddd',
    borderRadius: '4px',
    cursor: 'pointer',
    backgroundColor: '#fff',
    color: '#333'
  };

  const primaryButtonStyle = {
    ...buttonStyle,
    backgroundColor: '#000',
    color: 'white',
    border: '1px solid #000'
  };

  return (
    <div style={productCardStyle}>
      <h3>Product Editor</h3>
      
      {!isEditing ? (
        <div>
          <p><strong>Name:</strong> {product.name}</p>
          <p><strong>Price:</strong> ${product.price}</p>
          <p><strong>Category:</strong> {product.category}</p>
          <p><strong>In Stock:</strong> {product.inStock ? 'Yes' : 'No'}</p>
          
          <button 
            style={primaryButtonStyle}
            onClick={() => setIsEditing(true)}
          >
            Edit Product
          </button>
        </div>
      ) : (
        <form onSubmit={(e) => e.preventDefault()}>
          <div style={formGroupStyle}>
            <label style={labelStyle}>Name:</label>
            <input
              type="text"
              name="name"
              value={formData.name}
              onChange={handleInputChange}
              style={inputStyle}
            />
          </div>
          
          <div style={formGroupStyle}>
            <label style={labelStyle}>Price:</label>
            <input
              type="number"
              name="price"
              value={formData.price}
              onChange={handleInputChange}
              step="0.01"
              style={inputStyle}
            />
          </div>
          
          <div style={formGroupStyle}>
            <label style={labelStyle}>Category:</label>
            <select
              name="category"
              value={formData.category}
              onChange={handleInputChange}
              style={inputStyle}
            >
              <option value="Electronics">Electronics</option>
              <option value="Clothing">Clothing</option>
              <option value="Books">Books</option>
            </select>
          </div>
          
          <div style={formGroupStyle}>
            <label style={{...labelStyle, display: 'flex', alignItems: 'center'}}>
              <input
                type="checkbox"
                name="inStock"
                checked={formData.inStock}
                onChange={handleInputChange}
                style={{marginRight: '8px'}}
              />
              In Stock
            </label>
          </div>
          
          <div>
            <button 
              type="button"
              style={primaryButtonStyle}
              onClick={handleSave}
            >
              Save
            </button>
            <button 
              type="button"
              style={buttonStyle}
              onClick={handleCancel}
            >
              Cancel
            </button>
          </div>
        </form>
      )}
    </div>
  );
};

ProductEdit.displayName = 'ProductEdit';

export default ProductEdit;
