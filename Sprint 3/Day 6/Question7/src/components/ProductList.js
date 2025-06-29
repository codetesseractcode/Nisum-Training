import React, { useState, useEffect, useMemo } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useDebouncedSearch } from '../hooks/useDebounce';
import ProductCard from './ProductCard';
import ProductFilters from './ProductFilters';
import './ProductList.css';

const ProductList = () => {
  const [searchParams, setSearchParams] = useSearchParams();
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedCategory, setSelectedCategory] = useState(searchParams.get('category') || '');
  const [priceRange, setPriceRange] = useState({ min: '', max: '' });
  
  const {
    searchValue,
    debouncedSearchValue,
    setSearchValue,
    searchInputRef,
    clearSearch
  } = useDebouncedSearch('', 300);

  // Fetch products data
  useEffect(() => {
    const fetchProducts = async () => {
      try {
        setLoading(true);
        const response = await fetch('/products.json');
        if (!response.ok) {
          throw new Error('Failed to fetch products');
        }
        const data = await response.json();
        setProducts(data.products);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  // Get unique categories for the dropdown
  const categories = useMemo(() => {
    const uniqueCategories = [...new Set(products.map(product => product.category))];
    return uniqueCategories.sort();
  }, [products]);

  // Filter products based on search, category, and price range
  const filteredProducts = useMemo(() => {
    return products.filter(product => {
      // Search filter (name and description)
      const matchesSearch = debouncedSearchValue === '' || 
        product.name.toLowerCase().includes(debouncedSearchValue.toLowerCase()) ||
        product.description.toLowerCase().includes(debouncedSearchValue.toLowerCase());

      // Category filter
      const matchesCategory = selectedCategory === '' || 
        product.category === selectedCategory;

      // Price range filter
      const minPrice = priceRange.min === '' ? 0 : parseFloat(priceRange.min);
      const maxPrice = priceRange.max === '' ? Infinity : parseFloat(priceRange.max);
      const matchesPrice = product.price >= minPrice && product.price <= maxPrice;

      return matchesSearch && matchesCategory && matchesPrice;
    });
  }, [products, debouncedSearchValue, selectedCategory, priceRange]);

  // Handle filter changes
  const handleCategoryChange = (category) => {
    setSelectedCategory(category);
    // Update URL parameters
    if (category) {
      setSearchParams({ category });
    } else {
      setSearchParams({});
    }
  };

  const handlePriceRangeChange = (newPriceRange) => {
    setPriceRange(newPriceRange);
  };

  const clearAllFilters = () => {
    setSelectedCategory('');
    setPriceRange({ min: '', max: '' });
    clearSearch();
    setSearchParams({});
  };

  if (loading) {
    return (
      <div className="product-list-container">
        <div className="loading">
          <div className="loading-spinner"></div>
          <p>Loading products...</p>
        </div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="product-list-container">
        <div className="error">
          <p>Error: {error}</p>
          <button onClick={() => window.location.reload()}>Retry</button>
        </div>
      </div>
    );
  }

  return (
    <div className="product-list-container">
      <header className="product-list-header">
        <h1>Product Catalog</h1>
        <p>Discover our amazing collection of products</p>
      </header>

      <ProductFilters
        searchValue={searchValue}
        setSearchValue={setSearchValue}
        searchInputRef={searchInputRef}
        selectedCategory={selectedCategory}
        onCategoryChange={handleCategoryChange}
        priceRange={priceRange}
        onPriceRangeChange={handlePriceRangeChange}
        categories={categories}
        onClearFilters={clearAllFilters}
        resultsCount={filteredProducts.length}
        totalCount={products.length}
      />

      <div className="products-grid">
        {filteredProducts.length === 0 ? (
          <div className="no-products">
            <h3>No products found</h3>
            <p>Try adjusting your filters or search terms</p>
            <button onClick={clearAllFilters} className="clear-filters-btn">
              Clear All Filters
            </button>
          </div>
        ) : (
          filteredProducts.map(product => (
            <ProductCard key={product.id} product={product} />
          ))
        )}
      </div>
    </div>
  );
};

export default ProductList;
