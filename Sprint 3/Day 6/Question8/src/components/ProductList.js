import React, { useState, useEffect, useMemo } from 'react';
import { useSearchParams } from 'react-router-dom';
import { useDebouncedSearch } from '../hooks/useDebounce';
import { usePagination, useInfiniteScroll } from '../hooks/useInfiniteScroll';
import ProductCard from './ProductCard';
import ProductFilters from './ProductFilters';
import CategoryTabs from './CategoryTabs';
import LoadingSpinner from './LoadingSpinner';
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

  // Get unique categories for the dropdown and tabs
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

  // Pagination with infinite scroll
  const {
    displayedItems: displayedProducts,
    hasMore,
    loadMore,
    reset: resetPagination,
    displayedCount,
    totalItems: totalFilteredItems
  } = usePagination(filteredProducts, 12);

  // Infinite scroll functionality
  const { loadMoreRef, isFetching } = useInfiniteScroll(loadMore, hasMore);

  // Calculate category result counts for tabs
  const categoryResultCounts = useMemo(() => {
    const counts = {};
    categories.forEach(category => {
      counts[category] = products.filter(product => {
        const matchesSearch = debouncedSearchValue === '' || 
          product.name.toLowerCase().includes(debouncedSearchValue.toLowerCase()) ||
          product.description.toLowerCase().includes(debouncedSearchValue.toLowerCase());
        
        const matchesCategory = product.category === category;
        
        const minPrice = priceRange.min === '' ? 0 : parseFloat(priceRange.min);
        const maxPrice = priceRange.max === '' ? Infinity : parseFloat(priceRange.max);
        const matchesPrice = product.price >= minPrice && product.price <= maxPrice;
        
        return matchesSearch && matchesCategory && matchesPrice;
      }).length;
    });
    return counts;
  }, [products, categories, debouncedSearchValue, priceRange]);

  // Handle filter changes
  const handleCategoryChange = (category) => {
    setSelectedCategory(category);
    resetPagination(); // Reset pagination when category changes
    
    // Update URL parameters
    if (category) {
      setSearchParams({ category });
    } else {
      setSearchParams({});
    }
    
    // Scroll to top when changing category
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  const handlePriceRangeChange = (newPriceRange) => {
    setPriceRange(newPriceRange);
    resetPagination(); // Reset pagination when price range changes
  };

  const clearAllFilters = () => {
    setSelectedCategory('');
    setPriceRange({ min: '', max: '' });
    clearSearch();
    setSearchParams({});
    resetPagination();
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  // Reset pagination when search changes
  useEffect(() => {
    resetPagination();
  }, [debouncedSearchValue, resetPagination]);

  if (loading) {
    return (
      <div className="product-list-container">
        <LoadingSpinner size="large" text="Loading products..." />
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

      {/* Category Tabs */}
      <CategoryTabs
        categories={categories}
        selectedCategory={selectedCategory}
        onCategoryChange={handleCategoryChange}
        resultCounts={categoryResultCounts}
      />

      {/* Search and Filters */}
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
        resultsCount={totalFilteredItems}
        totalCount={products.length}
      />

      {/* Results Info */}
      <div className="results-info">
        <p>
          Showing {displayedCount} of {totalFilteredItems} products
          {selectedCategory && ` in ${selectedCategory}`}
          {debouncedSearchValue && ` matching "${debouncedSearchValue}"`}
        </p>
      </div>

      {/* Products Grid */}
      <div className="products-grid">
        {displayedProducts.length === 0 ? (
          <div className="no-products">
            <h3>No products found</h3>
            <p>Try adjusting your filters or search terms</p>
            <button onClick={clearAllFilters} className="clear-filters-btn">
              Clear All Filters
            </button>
          </div>
        ) : (
          displayedProducts.map(product => (
            <ProductCard key={product.id} product={product} />
          ))
        )}
      </div>

      {/* Load More Trigger */}
      {hasMore && (
        <div ref={loadMoreRef} className="load-more-trigger">
          {isFetching && (
            <LoadingSpinner 
              size="medium" 
              text="Loading more products..." 
              className="load-more-spinner"
            />
          )}
        </div>
      )}

      {/* Load More Button (fallback for users who prefer clicking) */}
      {hasMore && !isFetching && (
        <div className="load-more-button-container">
          <button onClick={loadMore} className="load-more-button">
            Load More Products ({totalFilteredItems - displayedCount} remaining)
          </button>
        </div>
      )}
    </div>
  );
};

export default ProductList;
