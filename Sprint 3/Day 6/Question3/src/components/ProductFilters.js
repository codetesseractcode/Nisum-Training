import React from 'react';
import './ProductFilters.css';

const ProductFilters = ({
  searchValue,
  setSearchValue,
  searchInputRef,
  selectedCategory,
  onCategoryChange,
  priceRange,
  onPriceRangeChange,
  categories,
  onClearFilters,
  resultsCount,
  totalCount
}) => {
  const handleMinPriceChange = (e) => {
    const value = e.target.value;
    onPriceRangeChange({ ...priceRange, min: value });
  };

  const handleMaxPriceChange = (e) => {
    const value = e.target.value;
    onPriceRangeChange({ ...priceRange, max: value });
  };

  const hasActiveFilters = searchValue || selectedCategory || priceRange.min || priceRange.max;

  return (
    <div className="product-filters">
      <div className="filters-section">
        <div className="filter-group">
          <label htmlFor="search-input">Search Products</label>
          <div className="search-input-container">
            <input
              id="search-input"
              ref={searchInputRef}
              type="text"
              placeholder="Search by name or description..."
              value={searchValue}
              onChange={(e) => setSearchValue(e.target.value)}
              className="search-input"
            />
            {searchValue && (
              <button
                type="button"
                onClick={() => setSearchValue('')}
                className="clear-search-btn"
                aria-label="Clear search"
              >
                ✕
              </button>
            )}
          </div>
        </div>

        <div className="filter-group">
          <label htmlFor="category-select">Category</label>
          <select
            id="category-select"
            value={selectedCategory}
            onChange={(e) => onCategoryChange(e.target.value)}
            className="category-select"
          >
            <option value="">All Categories</option>
            {categories.map(category => (
              <option key={category} value={category}>
                {category}
              </option>
            ))}
          </select>
        </div>

        <div className="filter-group">
          <label>Price Range</label>
          <div className="price-range-inputs">
            <div className="price-input-group">
              <label htmlFor="min-price">Min</label>
              <input
                id="min-price"
                type="number"
                placeholder="$0"
                value={priceRange.min}
                onChange={handleMinPriceChange}
                min="0"
                className="price-input"
              />
            </div>
            <span className="price-separator">to</span>
            <div className="price-input-group">
              <label htmlFor="max-price">Max</label>
              <input
                id="max-price"
                type="number"
                placeholder="No limit"
                value={priceRange.max}
                onChange={handleMaxPriceChange}
                min="0"
                className="price-input"
              />
            </div>
          </div>
        </div>

        {hasActiveFilters && (
          <div className="filter-group">
            <button
              onClick={onClearFilters}
              className="clear-all-btn"
            >
              Clear All Filters
            </button>
          </div>
        )}
      </div>

      <div className="results-summary">
        <span className="results-count">
          Showing {resultsCount} of {totalCount} products
        </span>
        {hasActiveFilters && (
          <span className="filter-indicator">
            (filtered)
          </span>
        )}
      </div>
    </div>
  );
};

export default ProductFilters;
