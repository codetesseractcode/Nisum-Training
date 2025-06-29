import { useState, useEffect, useCallback, useRef } from 'react';

/**
 * Custom hook for infinite scroll functionality
 * Uses IntersectionObserver to detect when to load more items
 */
export const useInfiniteScroll = (fetchMoreData, hasMore) => {
  const [isFetching, setIsFetching] = useState(false);
  const loadMoreRef = useRef(null);

  // Set up IntersectionObserver
  useEffect(() => {
    if (!loadMoreRef.current || !hasMore) return;

    const observer = new IntersectionObserver(
      (entries) => {
        const [entry] = entries;
        if (entry.isIntersecting && hasMore && !isFetching) {
          setIsFetching(true);
        }
      },
      {
        threshold: 0.1,
        rootMargin: '20px'
      }
    );

    observer.observe(loadMoreRef.current);

    return () => {
      if (loadMoreRef.current) {
        observer.unobserve(loadMoreRef.current);
      }
    };
  }, [hasMore, isFetching]);

  // Fetch more data when isFetching is true
  useEffect(() => {
    if (!isFetching || !hasMore) return;

    const loadMore = async () => {
      try {
        await fetchMoreData();
      } catch (error) {
        console.error('Error fetching more data:', error);
      } finally {
        setIsFetching(false);
      }
    };

    loadMore();
  }, [isFetching, fetchMoreData, hasMore]);

  return { loadMoreRef, isFetching };
};

/**
 * Custom hook for managing paginated data
 */
export const usePagination = (allItems, itemsPerPage = 12) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [displayedItems, setDisplayedItems] = useState([]);

  // Reset pagination when allItems changes
  useEffect(() => {
    setCurrentPage(1);
    setDisplayedItems(allItems.slice(0, itemsPerPage));
  }, [allItems, itemsPerPage]);

  // Load more items
  const loadMore = useCallback(() => {
    const nextPage = currentPage + 1;
    const newItems = allItems.slice(0, nextPage * itemsPerPage);
    setDisplayedItems(newItems);
    setCurrentPage(nextPage);
  }, [allItems, currentPage, itemsPerPage]);

  // Check if there are more items to load
  const hasMore = displayedItems.length < allItems.length;

  // Reset to first page
  const reset = useCallback(() => {
    setCurrentPage(1);
    setDisplayedItems(allItems.slice(0, itemsPerPage));
  }, [allItems, itemsPerPage]);

  return {
    displayedItems,
    hasMore,
    loadMore,
    reset,
    currentPage,
    totalItems: allItems.length,
    displayedCount: displayedItems.length
  };
};
