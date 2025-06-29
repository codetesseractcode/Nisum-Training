import { useState, useEffect, useRef } from 'react';

/**
 * Custom hook that debounces a value
 * @param {any} value - The value to debounce
 * @param {number} delay - The debounce delay in milliseconds
 * @returns {any} - The debounced value
 */
export const useDebounce = (value, delay) => {
  const [debouncedValue, setDebouncedValue] = useState(value);

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedValue(value);
    }, delay);

    // Cleanup function to cancel the timeout if value changes
    return () => {
      clearTimeout(handler);
    };
  }, [value, delay]);

  return debouncedValue;
};

/**
 * Custom hook for debounced search input
 * @param {string} initialValue - Initial search value
 * @param {number} delay - Debounce delay in milliseconds
 * @returns {object} - Object containing search value, debounced value, and setter
 */
export const useDebouncedSearch = (initialValue = '', delay = 300) => {
  const [searchValue, setSearchValue] = useState(initialValue);
  const debouncedSearchValue = useDebounce(searchValue, delay);
  const searchInputRef = useRef(null);

  const clearSearch = () => {
    setSearchValue('');
    if (searchInputRef.current) {
      searchInputRef.current.focus();
    }
  };

  return {
    searchValue,
    debouncedSearchValue,
    setSearchValue,
    searchInputRef,
    clearSearch
  };
};
