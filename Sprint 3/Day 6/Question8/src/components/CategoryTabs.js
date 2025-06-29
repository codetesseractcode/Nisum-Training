import React from 'react';
import './CategoryTabs.css';

const CategoryTabs = ({ 
  categories, 
  selectedCategory, 
  onCategoryChange, 
  resultCounts = {} 
}) => {
  const allCategories = ['All', ...categories];

  return (
    <div className="category-tabs">
      <div className="category-tabs-container">
        {allCategories.map((category) => {
          const isSelected = category === 'All' 
            ? selectedCategory === '' 
            : selectedCategory === category;
          
          const count = category === 'All' 
            ? Object.values(resultCounts).reduce((sum, count) => sum + count, 0)
            : resultCounts[category] || 0;

          return (
            <button
              key={category}
              onClick={() => onCategoryChange(category === 'All' ? '' : category)}
              className={`category-tab ${isSelected ? 'active' : ''}`}
              aria-pressed={isSelected}
            >
              <span className="category-name">{category}</span>
              <span className="category-count">({count})</span>
            </button>
          );
        })}
      </div>
    </div>
  );
};

export default CategoryTabs;
