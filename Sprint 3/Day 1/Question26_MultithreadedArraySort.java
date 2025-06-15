/**
 * Question 26: Multi-threaded Array Sorting
 */

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Arrays;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Parallel Merge Sort using Fork-Join framework
 */
class ParallelMergeSort extends RecursiveAction {
    private final int[] array;
    private final int low;
    private final int high;
    private final int threshold;
    
    public ParallelMergeSort(int[] array, int low, int high, int threshold) {
        this.array = array;
        this.low = low;
        this.high = high;
        this.threshold = threshold;
    }
    
    @Override
    protected void compute() {
        if (high - low <= threshold) {
            // Use sequential sort for small arrays
            Arrays.sort(array, low, high + 1);
        } else {
            int mid = low + (high - low) / 2;
            
            // Create subtasks
            ParallelMergeSort leftTask = new ParallelMergeSort(array, low, mid, threshold);
            ParallelMergeSort rightTask = new ParallelMergeSort(array, mid + 1, high, threshold);
            
            // Execute subtasks in parallel
            leftTask.fork();
            rightTask.compute();
            leftTask.join();
            
            // Merge the sorted halves
            merge(array, low, mid, high);
        }
    }
    
    private void merge(int[] array, int low, int mid, int high) {
        // Create temporary arrays for merging
        int[] left = Arrays.copyOfRange(array, low, mid + 1);
        int[] right = Arrays.copyOfRange(array, mid + 1, high + 1);
        
        int i = 0, j = 0, k = low;
        
        // Merge the arrays
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
        
        // Copy remaining elements
        while (i < left.length) {
            array[k++] = left[i++];
        }
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
}

/**
 * Parallel Quick Sort using ExecutorService
 */
class ParallelQuickSort {
    private final ExecutorService executor;
    private final int threshold;
    private final AtomicInteger tasksSubmitted = new AtomicInteger(0);
    private final AtomicInteger tasksCompleted = new AtomicInteger(0);
    
    public ParallelQuickSort(int numThreads, int threshold) {
        this.executor = Executors.newFixedThreadPool(numThreads);
        this.threshold = threshold;
    }
    
    public void sort(int[] array) {
        CountDownLatch latch = new CountDownLatch(1);
        tasksSubmitted.set(0);
        tasksCompleted.set(0);
        
        submitSortTask(array, 0, array.length - 1, latch);
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    private void submitSortTask(int[] array, int low, int high, CountDownLatch mainLatch) {
        if (high - low <= threshold) {
            // Sequential sort for small subarrays
            Arrays.sort(array, low, high + 1);
            if (tasksCompleted.incrementAndGet() == 1) {
                mainLatch.countDown();
            }
            return;
        }
        
        tasksSubmitted.incrementAndGet();
        
        executor.submit(() -> {
            try {
                int pivot = partition(array, low, high);
                
                CountDownLatch subLatch = new CountDownLatch(2);
                
                // Submit left partition
                executor.submit(() -> {
                    try {
                        if (low < pivot - 1) {
                            submitSortTask(array, low, pivot - 1, new CountDownLatch(1));
                        }
                    } finally {
                        subLatch.countDown();
                    }
                });
                
                // Submit right partition
                executor.submit(() -> {
                    try {
                        if (pivot + 1 < high) {
                            submitSortTask(array, pivot + 1, high, new CountDownLatch(1));
                        }
                    } finally {
                        subLatch.countDown();
                    }
                });
                
                subLatch.await();
                
                if (tasksCompleted.incrementAndGet() == tasksSubmitted.get()) {
                    mainLatch.countDown();
                }
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    
    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            if (array[j] <= pivot) {
                i++;
                swap(array, i, j);
            }
        }
        
        swap(array, i + 1, high);
        return i + 1;
    }
    
    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    public int getTasksSubmitted() { return tasksSubmitted.get(); }
    public int getTasksCompleted() { return tasksCompleted.get(); }
}

/**
 * Parallel Bucket Sort
 */
class ParallelBucketSort {
    private final int numThreads;
    private final ExecutorService executor;
    
    public ParallelBucketSort(int numThreads) {
        this.numThreads = numThreads;
        this.executor = Executors.newFixedThreadPool(numThreads);
    }
    
    public void sort(int[] array) {
        if (array.length <= 1) return;
        
        // Find min and max values
        int min = Arrays.stream(array).min().orElse(0);
        int max = Arrays.stream(array).max().orElse(0);
        
        if (min == max) return; // All elements are the same
        
        // Create buckets
        int bucketCount = Math.min(numThreads * 4, array.length / 10 + 1);
        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(Collections.synchronizedList(new ArrayList<>()));
        }
        
        // Distribute elements into buckets
        double range = (double) (max - min) / bucketCount;
        for (int value : array) {
            int bucketIndex = Math.min((int) ((value - min) / range), bucketCount - 1);
            buckets.get(bucketIndex).add(value);
        }
        
        // Sort buckets in parallel
        CountDownLatch latch = new CountDownLatch(bucketCount);
        
        for (int i = 0; i < bucketCount; i++) {
            final List<Integer> bucket = buckets.get(i);
            final int bucketIndex = i;
            
            executor.submit(() -> {
                try {
                    if (!bucket.isEmpty()) {
                        Collections.sort(bucket);
                        System.out.printf("Thread %s sorted bucket %d with %d elements%n", 
                                        Thread.currentThread().getName(), bucketIndex, bucket.size());
                    }
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        // Concatenate sorted buckets back to array
        int index = 0;
        for (List<Integer> bucket : buckets) {
            for (int value : bucket) {
                array[index++] = value;
            }
        }
    }
    
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Parallel Radix Sort
 */
class ParallelRadixSort {
    private final ExecutorService executor;
    private final int numThreads;
    
    public ParallelRadixSort(int numThreads) {
        this.numThreads = numThreads;
        this.executor = Executors.newFixedThreadPool(numThreads);
    }
    
    public void sort(int[] array) {
        if (array.length <= 1) return;
        
        // Find maximum number to determine number of digits
        int max = Arrays.stream(array).max().orElse(0);
        
        // Perform counting sort for every digit
        for (int exp = 1; max / exp > 0; exp *= 10) {
            parallelCountingSort(array, exp);
        }
    }
    
    private void parallelCountingSort(int[] array, int exp) {
        int n = array.length;
        int[] output = new int[n];
        int[] count = new int[10];
        
        // Count occurrences of each digit
        CountDownLatch countLatch = new CountDownLatch(numThreads);
        int chunkSize = n / numThreads;
        
        for (int t = 0; t < numThreads; t++) {
            final int start = t * chunkSize;
            final int end = (t == numThreads - 1) ? n : (t + 1) * chunkSize;
            
            executor.submit(() -> {
                try {
                    int[] localCount = new int[10];
                    for (int i = start; i < end; i++) {
                        localCount[(array[i] / exp) % 10]++;
                    }
                    
                    // Merge local counts (synchronized)
                    synchronized (count) {
                        for (int i = 0; i < 10; i++) {
                            count[i] += localCount[i];
                        }
                    }
                } finally {
                    countLatch.countDown();
                }
            });
        }
        
        try {
            countLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
        
        // Convert counts to actual positions
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        
        // Build output array
        for (int i = n - 1; i >= 0; i--) {
            int digit = (array[i] / exp) % 10;
            output[count[digit] - 1] = array[i];
            count[digit]--;
        }
        
        // Copy output array back to original array
        System.arraycopy(output, 0, array, 0, n);
    }
    
    public void shutdown() {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}

/**
 * Sorting performance tester
 */
class SortingPerformanceTester {
    
    public static int[] generateRandomArray(int size, int maxValue) {
        Random random = new Random(42); // Fixed seed for reproducible results
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(maxValue);
        }
        return array;
    }
    
    public static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    public static void printArraySample(int[] array, String label) {
        System.out.printf("%s (length=%d): [", label, array.length);
        int sampleSize = Math.min(10, array.length);
        for (int i = 0; i < sampleSize; i++) {
            System.out.print(array[i]);
            if (i < sampleSize - 1) System.out.print(", ");
        }
        if (array.length > sampleSize) {
            System.out.print("...");
        }
        System.out.println("]");
    }
}

/**
 * Main class demonstrating multi-threaded array sorting
 */
public class Question26_MultithreadedArraySort {
    
    private static final int ARRAY_SIZE = 100000;
    private static final int MAX_VALUE = 10000;
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    
    /**
     * Test Fork-Join Merge Sort
     */
    public static void testParallelMergeSort() {
        System.out.println("=== Testing Parallel Merge Sort (Fork-Join) ===");
        
        int[] array = SortingPerformanceTester.generateRandomArray(ARRAY_SIZE, MAX_VALUE);
        SortingPerformanceTester.printArraySample(array, "Original array");
        
        ForkJoinPool forkJoinPool = new ForkJoinPool(NUM_THREADS);
        
        long startTime = System.currentTimeMillis();
        
        ParallelMergeSort task = new ParallelMergeSort(array, 0, array.length - 1, 1000);
        forkJoinPool.invoke(task);
        
        long endTime = System.currentTimeMillis();
        
        SortingPerformanceTester.printArraySample(array, "Sorted array");
        System.out.printf("Execution time: %d ms%n", endTime - startTime);
        System.out.printf("Array is sorted: %s%n", SortingPerformanceTester.isSorted(array));
        System.out.printf("Active threads: %d%n", forkJoinPool.getActiveThreadCount());
        
        forkJoinPool.shutdown();
        System.out.println();
    }
    
    /**
     * Test Parallel Quick Sort
     */
    public static void testParallelQuickSort() {
        System.out.println("=== Testing Parallel Quick Sort ===");
        
        int[] array = SortingPerformanceTester.generateRandomArray(ARRAY_SIZE, MAX_VALUE);
        SortingPerformanceTester.printArraySample(array, "Original array");
        
        ParallelQuickSort sorter = new ParallelQuickSort(NUM_THREADS, 1000);
        
        long startTime = System.currentTimeMillis();
        sorter.sort(array);
        long endTime = System.currentTimeMillis();
        
        SortingPerformanceTester.printArraySample(array, "Sorted array");
        System.out.printf("Execution time: %d ms%n", endTime - startTime);
        System.out.printf("Array is sorted: %s%n", SortingPerformanceTester.isSorted(array));
        System.out.printf("Tasks submitted: %d, completed: %d%n", 
                        sorter.getTasksSubmitted(), sorter.getTasksCompleted());
        
        sorter.shutdown();
        System.out.println();
    }
    
    /**
     * Test Parallel Bucket Sort
     */
    public static void testParallelBucketSort() {
        System.out.println("=== Testing Parallel Bucket Sort ===");
        
        int[] array = SortingPerformanceTester.generateRandomArray(ARRAY_SIZE, MAX_VALUE);
        SortingPerformanceTester.printArraySample(array, "Original array");
        
        ParallelBucketSort sorter = new ParallelBucketSort(NUM_THREADS);
        
        long startTime = System.currentTimeMillis();
        sorter.sort(array);
        long endTime = System.currentTimeMillis();
        
        SortingPerformanceTester.printArraySample(array, "Sorted array");
        System.out.printf("Execution time: %d ms%n", endTime - startTime);
        System.out.printf("Array is sorted: %s%n", SortingPerformanceTester.isSorted(array));
        
        sorter.shutdown();
        System.out.println();
    }
    
    /**
     * Test Parallel Radix Sort
     */
    public static void testParallelRadixSort() {
        System.out.println("=== Testing Parallel Radix Sort ===");
        
        int[] array = SortingPerformanceTester.generateRandomArray(ARRAY_SIZE, MAX_VALUE);
        SortingPerformanceTester.printArraySample(array, "Original array");
        
        ParallelRadixSort sorter = new ParallelRadixSort(NUM_THREADS);
        
        long startTime = System.currentTimeMillis();
        sorter.sort(array);
        long endTime = System.currentTimeMillis();
        
        SortingPerformanceTester.printArraySample(array, "Sorted array");
        System.out.printf("Execution time: %d ms%n", endTime - startTime);
        System.out.printf("Array is sorted: %s%n", SortingPerformanceTester.isSorted(array));
        
        sorter.shutdown();
        System.out.println();
    }
    
    /**
     * Performance comparison of different sorting approaches
     */
    public static void performanceComparison() {
        System.out.println("=== Performance Comparison ===");
        
        int[] testSizes = {10000, 50000, 100000};
        
        for (int size : testSizes) {
            System.out.printf("Array size: %d%n", size);
            
            // Sequential Arrays.sort()
            int[] array1 = SortingPerformanceTester.generateRandomArray(size, MAX_VALUE);
            long start = System.currentTimeMillis();
            Arrays.sort(array1);
            long sequentialTime = System.currentTimeMillis() - start;
            
            // Parallel Merge Sort
            int[] array2 = SortingPerformanceTester.generateRandomArray(size, MAX_VALUE);
            ForkJoinPool fjp = new ForkJoinPool(NUM_THREADS);
            start = System.currentTimeMillis();
            fjp.invoke(new ParallelMergeSort(array2, 0, array2.length - 1, 1000));
            long mergeSortTime = System.currentTimeMillis() - start;
            fjp.shutdown();
            
            // Parallel Bucket Sort
            int[] array3 = SortingPerformanceTester.generateRandomArray(size, MAX_VALUE);
            ParallelBucketSort bucketSorter = new ParallelBucketSort(NUM_THREADS);
            start = System.currentTimeMillis();
            bucketSorter.sort(array3);
            long bucketSortTime = System.currentTimeMillis() - start;
            bucketSorter.shutdown();
            
            // Parallel Arrays.parallelSort()
            int[] array4 = SortingPerformanceTester.generateRandomArray(size, MAX_VALUE);
            start = System.currentTimeMillis();
            Arrays.parallelSort(array4);
            long parallelSortTime = System.currentTimeMillis() - start;
            
            System.out.printf("  Sequential: %d ms%n", sequentialTime);
            System.out.printf("  Parallel Merge: %d ms (%.2fx)%n", 
                            mergeSortTime, (double) sequentialTime / mergeSortTime);
            System.out.printf("  Parallel Bucket: %d ms (%.2fx)%n", 
                            bucketSortTime, (double) sequentialTime / bucketSortTime);
            System.out.printf("  Arrays.parallelSort: %d ms (%.2fx)%n", 
                            parallelSortTime, (double) sequentialTime / parallelSortTime);
            System.out.println();
        }
    }
    
    /**
     * Test sorting with different data patterns
     */
    public static void testDifferentDataPatterns() {
        System.out.println("=== Testing Different Data Patterns ===");
        
        int size = 50000;
        
        // Random data
        System.out.println("Random data:");
        int[] randomArray = SortingPerformanceTester.generateRandomArray(size, MAX_VALUE);
        testSortingAlgorithm(randomArray, "Random");
        
        // Already sorted data
        System.out.println("Already sorted data:");
        int[] sortedArray = SortingPerformanceTester.generateRandomArray(size, MAX_VALUE);
        Arrays.sort(sortedArray);
        testSortingAlgorithm(Arrays.copyOf(sortedArray, sortedArray.length), "Sorted");
        
        // Reverse sorted data
        System.out.println("Reverse sorted data:");
        int[] reverseArray = Arrays.copyOf(sortedArray, sortedArray.length);
        for (int i = 0; i < reverseArray.length / 2; i++) {
            int temp = reverseArray[i];
            reverseArray[i] = reverseArray[reverseArray.length - 1 - i];
            reverseArray[reverseArray.length - 1 - i] = temp;
        }
        testSortingAlgorithm(reverseArray, "Reverse");
        
        // Mostly sorted data
        System.out.println("Mostly sorted data (10% random swaps):");
        int[] mostlySortedArray = Arrays.copyOf(sortedArray, sortedArray.length);
        Random random = new Random(42);
        for (int i = 0; i < size / 10; i++) {
            int idx1 = random.nextInt(size);
            int idx2 = random.nextInt(size);
            int temp = mostlySortedArray[idx1];
            mostlySortedArray[idx1] = mostlySortedArray[idx2];
            mostlySortedArray[idx2] = temp;
        }
        testSortingAlgorithm(mostlySortedArray, "MostlySorted");
        
        System.out.println();
    }
    
    private static void testSortingAlgorithm(int[] array, String dataType) {
        ParallelBucketSort sorter = new ParallelBucketSort(NUM_THREADS);
        
        long start = System.currentTimeMillis();
        sorter.sort(array);
        long time = System.currentTimeMillis() - start;
        
        System.out.printf("  %s: %d ms, Sorted: %s%n", 
                        dataType, time, SortingPerformanceTester.isSorted(array));
        
        sorter.shutdown();
    }
    
    public static void main(String[] args) {
        System.out.println("=== Question 26: Multi-threaded Array Sorting ===");
        System.out.printf("Available processors: %d%n", NUM_THREADS);
        System.out.printf("Test array size: %d%n", ARRAY_SIZE);
        System.out.println();
        
        testParallelMergeSort();
        testParallelQuickSort();
        testParallelBucketSort();
        testParallelRadixSort();
        performanceComparison();
        testDifferentDataPatterns();
        
        System.out.println("=== Key Concepts Demonstrated ===");
        System.out.println("• Fork-Join framework for divide-and-conquer algorithms");
        System.out.println("• ExecutorService for thread pool management");
        System.out.println("• Parallel merge sort with recursive task decomposition");
        System.out.println("• Parallel bucket sort with synchronized collections");
        System.out.println("• Performance comparison of sequential vs parallel sorting");
        System.out.println("• Load balancing and work stealing in parallel algorithms");
        System.out.println("• Optimal threshold selection for parallel vs sequential execution");
        System.out.println("• Data pattern impact on sorting performance");
    }
}
