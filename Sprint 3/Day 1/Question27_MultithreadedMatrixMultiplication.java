/**
 * Question 27: Multi-threaded Matrix Multiplication
 */

import java.util.concurrent.*;
import java.util.Random;

/**
 * Matrix class with utility methods
 */
class Matrix {
    private final double[][] data;
    private final int rows;
    private final int cols;
    
    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new double[rows][cols];
    }
    
    public Matrix(double[][] data) {
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(data[i], 0, this.data[i], 0, cols);
        }
    }
    
    public int getRows() { return rows; }
    public int getCols() { return cols; }
    
    public double get(int row, int col) { return data[row][col]; }
    public void set(int row, int col, double value) { data[row][col] = value; }
    
    public double[][] getData() { return data; }
    
    /**
     * Fill matrix with random values
     */
    public void fillRandom(Random random, double min, double max) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                data[i][j] = min + random.nextDouble() * (max - min);
            }
        }
    }
    
    /**
     * Create identity matrix
     */
    public static Matrix identity(int size) {
        Matrix matrix = new Matrix(size, size);
        for (int i = 0; i < size; i++) {
            matrix.set(i, i, 1.0);
        }
        return matrix;
    }
    
    /**
     * Get submatrix
     */
    public Matrix getSubmatrix(int startRow, int endRow, int startCol, int endCol) {
        Matrix sub = new Matrix(endRow - startRow, endCol - startCol);
        for (int i = 0; i < sub.rows; i++) {
            for (int j = 0; j < sub.cols; j++) {
                sub.set(i, j, this.get(startRow + i, startCol + j));
            }
        }
        return sub;
    }
    
    /**
     * Set submatrix values
     */
    public void setSubmatrix(int startRow, int startCol, Matrix sub) {
        for (int i = 0; i < sub.rows; i++) {
            for (int j = 0; j < sub.cols; j++) {
                this.set(startRow + i, startCol + j, sub.get(i, j));
            }
        }
    }
    
    /**
     * Matrix addition
     */
    public Matrix add(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrix dimensions must match");
        }
        
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j) + other.get(i, j));
            }
        }
        return result;
    }
    
    /**
     * Matrix subtraction
     */
    public Matrix subtract(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Matrix dimensions must match");
        }
        
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.set(i, j, this.get(i, j) - other.get(i, j));
            }
        }
        return result;
    }
    
    /**
     * Check if matrices are approximately equal
     */
    public boolean equals(Matrix other, double tolerance) {
        if (this.rows != other.rows || this.cols != other.cols) {
            return false;
        }
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Math.abs(this.get(i, j) - other.get(i, j)) > tolerance) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Print matrix sample
     */
    public void printSample(String label, int maxSize) {
        System.out.printf("%s (%dx%d):%n", label, rows, cols);
        int displayRows = Math.min(maxSize, rows);
        int displayCols = Math.min(maxSize, cols);
        
        for (int i = 0; i < displayRows; i++) {
            System.out.print("  [");
            for (int j = 0; j < displayCols; j++) {
                System.out.printf("%8.2f", data[i][j]);
                if (j < displayCols - 1) System.out.print(", ");
            }
            if (cols > maxSize) System.out.print(", ...");
            System.out.println("]");
        }
        if (rows > maxSize) {
            System.out.println("  ...");
        }
        System.out.println();
    }
}

/**
 * Sequential matrix multiplication for comparison
 */
class SequentialMatrixMultiplier {
    
    public static Matrix multiply(Matrix a, Matrix b) {
        if (a.getCols() != b.getRows()) {
            throw new IllegalArgumentException("Matrix dimensions incompatible for multiplication");
        }
        
        Matrix result = new Matrix(a.getRows(), b.getCols());
        
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < b.getCols(); j++) {
                double sum = 0;
                for (int k = 0; k < a.getCols(); k++) {
                    sum += a.get(i, k) * b.get(k, j);
                }
                result.set(i, j, sum);
            }
        }
        
        return result;
    }
}

/**
 * Row-wise parallel matrix multiplication
 */
class RowWiseParallelMultiplier {
    private final ExecutorService executor;
    
    public RowWiseParallelMultiplier(int numThreads) {
        this.executor = Executors.newFixedThreadPool(numThreads);
    }
    
    public Matrix multiply(Matrix a, Matrix b) {
        if (a.getCols() != b.getRows()) {
            throw new IllegalArgumentException("Matrix dimensions incompatible for multiplication");
        }
        
        Matrix result = new Matrix(a.getRows(), b.getCols());
        CountDownLatch latch = new CountDownLatch(a.getRows());
        
        for (int i = 0; i < a.getRows(); i++) {
            final int row = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < b.getCols(); j++) {
                        double sum = 0;
                        for (int k = 0; k < a.getCols(); k++) {
                            sum += a.get(row, k) * b.get(k, j);
                        }
                        result.set(row, j, sum);
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
            throw new RuntimeException("Matrix multiplication interrupted", e);
        }
        
        return result;
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
 * Block-wise parallel matrix multiplication for better cache performance
 */
class BlockWiseParallelMultiplier {
    private final ExecutorService executor;
    private final int blockSize;
    
    public BlockWiseParallelMultiplier(int numThreads, int blockSize) {
        this.executor = Executors.newFixedThreadPool(numThreads);
        this.blockSize = blockSize;
    }
    
    public Matrix multiply(Matrix a, Matrix b) {
        if (a.getCols() != b.getRows()) {
            throw new IllegalArgumentException("Matrix dimensions incompatible for multiplication");
        }
        
        Matrix result = new Matrix(a.getRows(), b.getCols());
        
        int numBlocksI = (a.getRows() + blockSize - 1) / blockSize;
        int numBlocksJ = (b.getCols() + blockSize - 1) / blockSize;
        int numBlocksK = (a.getCols() + blockSize - 1) / blockSize;
        
        CountDownLatch latch = new CountDownLatch(numBlocksI * numBlocksJ);
        
        for (int bi = 0; bi < numBlocksI; bi++) {
            for (int bj = 0; bj < numBlocksJ; bj++) {
                final int blockI = bi;
                final int blockJ = bj;
                
                executor.submit(() -> {
                    try {
                        int startI = blockI * blockSize;
                        int endI = Math.min(startI + blockSize, a.getRows());
                        int startJ = blockJ * blockSize;
                        int endJ = Math.min(startJ + blockSize, b.getCols());
                        
                        for (int bk = 0; bk < numBlocksK; bk++) {
                            int startK = bk * blockSize;
                            int endK = Math.min(startK + blockSize, a.getCols());
                            
                            // Multiply blocks
                            for (int i = startI; i < endI; i++) {
                                for (int j = startJ; j < endJ; j++) {
                                    double sum = result.get(i, j);
                                    for (int k = startK; k < endK; k++) {
                                        sum += a.get(i, k) * b.get(k, j);
                                    }
                                    result.set(i, j, sum);
                                }
                            }
                        }
                    } finally {
                        latch.countDown();
                    }
                });
            }
        }
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Matrix multiplication interrupted", e);
        }
        
        return result;
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
 * Fork-Join parallel matrix multiplication
 */
class ForkJoinMatrixMultiplier extends RecursiveTask<Matrix> {
    private final Matrix a;
    private final Matrix b;
    private final int threshold;
    
    public ForkJoinMatrixMultiplier(Matrix a, Matrix b, int threshold) {
        this.a = a;
        this.b = b;
        this.threshold = threshold;
    }
    
    @Override
    protected Matrix compute() {
        if (a.getRows() <= threshold || a.getCols() <= threshold || b.getCols() <= threshold) {
            return SequentialMatrixMultiplier.multiply(a, b);
        }
        
        // Divide matrices into quadrants
        int midA = a.getRows() / 2;
        int midB = a.getCols() / 2;
        int midC = b.getCols() / 2;
        
        Matrix a11 = a.getSubmatrix(0, midA, 0, midB);
        Matrix a12 = a.getSubmatrix(0, midA, midB, a.getCols());
        Matrix a21 = a.getSubmatrix(midA, a.getRows(), 0, midB);
        Matrix a22 = a.getSubmatrix(midA, a.getRows(), midB, a.getCols());
        
        Matrix b11 = b.getSubmatrix(0, midB, 0, midC);
        Matrix b12 = b.getSubmatrix(0, midB, midC, b.getCols());
        Matrix b21 = b.getSubmatrix(midB, b.getRows(), 0, midC);
        Matrix b22 = b.getSubmatrix(midB, b.getRows(), midC, b.getCols());
        
        // Create subtasks
        ForkJoinMatrixMultiplier task1 = new ForkJoinMatrixMultiplier(a11, b11, threshold);
        ForkJoinMatrixMultiplier task2 = new ForkJoinMatrixMultiplier(a12, b21, threshold);
        ForkJoinMatrixMultiplier task3 = new ForkJoinMatrixMultiplier(a11, b12, threshold);
        ForkJoinMatrixMultiplier task4 = new ForkJoinMatrixMultiplier(a12, b22, threshold);
        ForkJoinMatrixMultiplier task5 = new ForkJoinMatrixMultiplier(a21, b11, threshold);
        ForkJoinMatrixMultiplier task6 = new ForkJoinMatrixMultiplier(a22, b21, threshold);
        ForkJoinMatrixMultiplier task7 = new ForkJoinMatrixMultiplier(a21, b12, threshold);
        ForkJoinMatrixMultiplier task8 = new ForkJoinMatrixMultiplier(a22, b22, threshold);
        
        // Fork subtasks
        task1.fork();
        task2.fork();
        task3.fork();
        task4.fork();
        task5.fork();
        task6.fork();
        task7.fork();        // Compute one task directly and join others
        Matrix c22_part2 = task8.compute();  // a22*b22
        Matrix c22_part1 = task7.join();     // a21*b12  
        Matrix c21_part1 = task6.join();     // a22*b21
        Matrix c11_part2 = task5.join();     // a21*b11
        Matrix c12_part2 = task4.join();     // a12*b22
        Matrix c11_part1 = task3.join();     // a11*b12
        Matrix c12_part1 = task2.join();     // a12*b21
        Matrix c11_part0 = task1.join();     // a11*b11
        
        // Combine results according to matrix multiplication formula
        Matrix c11 = c11_part0.add(c12_part1);  // a11*b11 + a12*b21
        Matrix c12 = c11_part1.add(c12_part2);  // a11*b12 + a12*b22  
        Matrix c21 = c11_part2.add(c21_part1);  // a21*b11 + a22*b21
        Matrix c22 = c22_part1.add(c22_part2);  // a21*b12 + a22*b22
        
        // Assemble final result
        Matrix result = new Matrix(a.getRows(), b.getCols());
        result.setSubmatrix(0, 0, c11);
        result.setSubmatrix(0, midC, c12);
        result.setSubmatrix(midA, 0, c21);
        result.setSubmatrix(midA, midC, c22);
        
        return result;
    }
}

/**
 * Strassen's algorithm with parallelization
 */
class ParallelStrassenMultiplier {
    private final ForkJoinPool forkJoinPool;
    private final int threshold;
    
    public ParallelStrassenMultiplier(int numThreads, int threshold) {
        this.forkJoinPool = new ForkJoinPool(numThreads);
        this.threshold = threshold;
    }
    
    public Matrix multiply(Matrix a, Matrix b) {
        // Ensure matrices are square and power of 2 for simplicity
        int maxSize = Math.max(Math.max(a.getRows(), a.getCols()), 
                              Math.max(b.getRows(), b.getCols()));
        int paddedSize = Integer.highestOneBit(maxSize);
        if (paddedSize < maxSize) paddedSize *= 2;
        
        Matrix paddedA = padMatrix(a, paddedSize);
        Matrix paddedB = padMatrix(b, paddedSize);
        
        Matrix result = forkJoinPool.invoke(new StrassenTask(paddedA, paddedB, threshold));
        
        return result.getSubmatrix(0, a.getRows(), 0, b.getCols());
    }
    
    private Matrix padMatrix(Matrix matrix, int newSize) {
        Matrix padded = new Matrix(newSize, newSize);
        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getCols(); j++) {
                padded.set(i, j, matrix.get(i, j));
            }
        }
        return padded;
    }
    
    public void shutdown() {
        forkJoinPool.shutdown();
        try {
            if (!forkJoinPool.awaitTermination(60, TimeUnit.SECONDS)) {
                forkJoinPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            forkJoinPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
    
    private static class StrassenTask extends RecursiveTask<Matrix> {
        private final Matrix a;
        private final Matrix b;
        private final int threshold;
        
        public StrassenTask(Matrix a, Matrix b, int threshold) {
            this.a = a;
            this.b = b;
            this.threshold = threshold;
        }
        
        @Override
        protected Matrix compute() {
            int n = a.getRows();
            
            if (n <= threshold) {
                return SequentialMatrixMultiplier.multiply(a, b);
            }
            
            int mid = n / 2;
            
            // Divide matrices
            Matrix a11 = a.getSubmatrix(0, mid, 0, mid);
            Matrix a12 = a.getSubmatrix(0, mid, mid, n);
            Matrix a21 = a.getSubmatrix(mid, n, 0, mid);
            Matrix a22 = a.getSubmatrix(mid, n, mid, n);
            
            Matrix b11 = b.getSubmatrix(0, mid, 0, mid);
            Matrix b12 = b.getSubmatrix(0, mid, mid, n);
            Matrix b21 = b.getSubmatrix(mid, n, 0, mid);
            Matrix b22 = b.getSubmatrix(mid, n, mid, n);
            
            // Calculate the 7 products recursively
            StrassenTask[] tasks = new StrassenTask[7];
            tasks[0] = new StrassenTask(a11.add(a22), b11.add(b22), threshold); // M1
            tasks[1] = new StrassenTask(a21.add(a22), b11, threshold);           // M2
            tasks[2] = new StrassenTask(a11, b12.subtract(b22), threshold);      // M3
            tasks[3] = new StrassenTask(a22, b21.subtract(b11), threshold);      // M4
            tasks[4] = new StrassenTask(a11.add(a12), b22, threshold);           // M5
            tasks[5] = new StrassenTask(a21.subtract(a11), b11.add(b12), threshold); // M6
            tasks[6] = new StrassenTask(a12.subtract(a22), b21.add(b22), threshold); // M7
            
            // Fork all tasks except the last one
            for (int i = 0; i < 6; i++) {
                tasks[i].fork();
            }
            
            // Compute last task directly
            Matrix m7 = tasks[6].compute();
            
            // Join all other tasks
            Matrix m1 = tasks[0].join();
            Matrix m2 = tasks[1].join();
            Matrix m3 = tasks[2].join();
            Matrix m4 = tasks[3].join();
            Matrix m5 = tasks[4].join();
            Matrix m6 = tasks[5].join();
            
            // Calculate result quadrants
            Matrix c11 = m1.add(m4).subtract(m5).add(m7);
            Matrix c12 = m3.add(m5);
            Matrix c21 = m2.add(m4);
            Matrix c22 = m1.subtract(m2).add(m3).add(m6);
            
            // Combine quadrants
            Matrix result = new Matrix(n, n);
            result.setSubmatrix(0, 0, c11);
            result.setSubmatrix(0, mid, c12);
            result.setSubmatrix(mid, 0, c21);
            result.setSubmatrix(mid, mid, c22);
            
            return result;
        }
    }
}

/**
 * Main class demonstrating multi-threaded matrix multiplication
 */
public class Question27_MultithreadedMatrixMultiplication {
    
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
    
    /**
     * Test different matrix multiplication approaches
     */
    public static void testMatrixMultiplication() {
        System.out.println("=== Testing Matrix Multiplication Approaches ===");
        
        int size = 200;
        Random random = new Random(42);
        
        Matrix a = new Matrix(size, size);
        Matrix b = new Matrix(size, size);
        a.fillRandom(random, -10, 10);
        b.fillRandom(random, -10, 10);
        
        a.printSample("Matrix A", 3);
        b.printSample("Matrix B", 3);
        
        // Sequential multiplication
        System.out.println("Sequential multiplication:");
        long start = System.currentTimeMillis();
        Matrix sequentialResult = SequentialMatrixMultiplier.multiply(a, b);
        long sequentialTime = System.currentTimeMillis() - start;
        sequentialResult.printSample("Sequential Result", 3);
        System.out.printf("Time: %d ms%n%n", sequentialTime);
        
        // Row-wise parallel multiplication
        System.out.println("Row-wise parallel multiplication:");
        RowWiseParallelMultiplier rowMultiplier = new RowWiseParallelMultiplier(NUM_THREADS);
        start = System.currentTimeMillis();
        Matrix rowResult = rowMultiplier.multiply(a, b);
        long rowTime = System.currentTimeMillis() - start;
        rowResult.printSample("Row-wise Result", 3);
        System.out.printf("Time: %d ms (%.2fx speedup)%n", rowTime, (double) sequentialTime / rowTime);
        System.out.printf("Results match: %s%n%n", sequentialResult.equals(rowResult, 1e-10));
        rowMultiplier.shutdown();
        
        // Block-wise parallel multiplication
        System.out.println("Block-wise parallel multiplication:");
        BlockWiseParallelMultiplier blockMultiplier = new BlockWiseParallelMultiplier(NUM_THREADS, 50);
        start = System.currentTimeMillis();
        Matrix blockResult = blockMultiplier.multiply(a, b);
        long blockTime = System.currentTimeMillis() - start;
        blockResult.printSample("Block-wise Result", 3);
        System.out.printf("Time: %d ms (%.2fx speedup)%n", blockTime, (double) sequentialTime / blockTime);
        System.out.printf("Results match: %s%n%n", sequentialResult.equals(blockResult, 1e-10));
        blockMultiplier.shutdown();
          // Fork-Join multiplication
        System.out.println("Fork-Join parallel multiplication:");
        try (ForkJoinPool forkJoinPool = new ForkJoinPool(NUM_THREADS)) {
            start = System.currentTimeMillis();
            Matrix forkJoinResult = forkJoinPool.invoke(new ForkJoinMatrixMultiplier(a, b, 32));
            long forkJoinTime = System.currentTimeMillis() - start;
            forkJoinResult.printSample("Fork-Join Result", 3);
            System.out.printf("Time: %d ms (%.2fx speedup)%n", forkJoinTime, (double) sequentialTime / forkJoinTime);
            System.out.printf("Results match: %s%n%n", sequentialResult.equals(forkJoinResult, 1e-10));
        }
    }
    
    /**
     * Performance comparison with different matrix sizes
     */
    public static void performanceComparison() {
        System.out.println("=== Performance Comparison ===");
        
        int[] sizes = {100, 200, 400, 800};
        
        for (int size : sizes) {
            System.out.printf("Matrix size: %dx%d%n", size, size);
            
            Random random = new Random(42);
            Matrix a = new Matrix(size, size);
            Matrix b = new Matrix(size, size);
            a.fillRandom(random, -10, 10);
            b.fillRandom(random, -10, 10);
            
            // Sequential
            long start = System.currentTimeMillis();
            Matrix seqResult = SequentialMatrixMultiplier.multiply(a, b);
            long seqTime = System.currentTimeMillis() - start;
            
            // Row-wise parallel
            RowWiseParallelMultiplier rowMultiplier = new RowWiseParallelMultiplier(NUM_THREADS);
            start = System.currentTimeMillis();
            Matrix rowResult = rowMultiplier.multiply(a, b);
            long rowTime = System.currentTimeMillis() - start;
            rowMultiplier.shutdown();
            
            // Block-wise parallel
            BlockWiseParallelMultiplier blockMultiplier = new BlockWiseParallelMultiplier(NUM_THREADS, 64);
            start = System.currentTimeMillis();
            Matrix blockResult = blockMultiplier.multiply(a, b);
            long blockTime = System.currentTimeMillis() - start;
            blockMultiplier.shutdown();
            
            System.out.printf("  Sequential: %d ms%n", seqTime);
            System.out.printf("  Row-wise: %d ms (%.2fx)%n", rowTime, (double) seqTime / rowTime);
            System.out.printf("  Block-wise: %d ms (%.2fx)%n", blockTime, (double) seqTime / blockTime);
            System.out.printf("  Results match: %s%n%n", 
                            seqResult.equals(rowResult, 1e-10) && seqResult.equals(blockResult, 1e-10));
        }
    }
    
    /**
     * Test impact of block size on performance
     */
    public static void testBlockSizeImpact() {
        System.out.println("=== Block Size Impact Analysis ===");
        
        int size = 400;
        int[] blockSizes = {16, 32, 64, 128, 200};
        
        Random random = new Random(42);
        Matrix a = new Matrix(size, size);
        Matrix b = new Matrix(size, size);
        a.fillRandom(random, -10, 10);
        b.fillRandom(random, -10, 10);
          for (int blockSize : blockSizes) {
            BlockWiseParallelMultiplier multiplier = new BlockWiseParallelMultiplier(NUM_THREADS, blockSize);
            
            long start = System.currentTimeMillis();
            multiplier.multiply(a, b); // Result computed but not stored to save memory
            long time = System.currentTimeMillis() - start;
            
            System.out.printf("Block size %d: %d ms%n", blockSize, time);
            
            multiplier.shutdown();
        }
        System.out.println();
    }
    
    /**
     * Test scalability with different thread counts
     */
    public static void testScalability() {
        System.out.println("=== Thread Scalability Analysis ===");
        
        int size = 300;
        int[] threadCounts = {1, 2, 4, 8, 16};
        
        Random random = new Random(42);
        Matrix a = new Matrix(size, size);
        Matrix b = new Matrix(size, size);
        a.fillRandom(random, -10, 10);
        b.fillRandom(random, -10, 10);
          for (int threads : threadCounts) {
            if (threads > NUM_THREADS) continue;
            
            RowWiseParallelMultiplier multiplier = new RowWiseParallelMultiplier(threads);
            
            long start = System.currentTimeMillis();
            multiplier.multiply(a, b); // Result computed but not stored to save memory
            long time = System.currentTimeMillis() - start;
            
            System.out.printf("%d threads: %d ms%n", threads, time);
            
            multiplier.shutdown();
        }
        System.out.println();
    }
    
    /**
     * Memory usage and cache performance test
     */
    public static void testMemoryPerformance() {
        System.out.println("=== Memory and Cache Performance ===");
        
        Runtime runtime = Runtime.getRuntime();
        
        int size = 500;
        Random random = new Random(42);
        Matrix a = new Matrix(size, size);
        Matrix b = new Matrix(size, size);
        a.fillRandom(random, -10, 10);
        b.fillRandom(random, -10, 10);
          // Force garbage collection
        System.gc();
        long memBefore = runtime.totalMemory() - runtime.freeMemory();
        
        // Row-wise multiplication
        RowWiseParallelMultiplier rowMultiplier = new RowWiseParallelMultiplier(NUM_THREADS);
        long start = System.currentTimeMillis();
        rowMultiplier.multiply(a, b); // Result computed but not stored to save memory
        long rowTime = System.currentTimeMillis() - start;
        
        System.gc();
        long memAfterRow = runtime.totalMemory() - runtime.freeMemory();
        
        System.out.printf("Row-wise: %d ms, Memory used: %d KB%n", 
                        rowTime, (memAfterRow - memBefore) / 1024);
        
        rowMultiplier.shutdown();
          // Block-wise multiplication
        System.gc();
        memBefore = runtime.totalMemory() - runtime.freeMemory();
        
        BlockWiseParallelMultiplier blockMultiplier = new BlockWiseParallelMultiplier(NUM_THREADS, 64);
        start = System.currentTimeMillis();
        blockMultiplier.multiply(a, b); // Result computed but not stored to save memory
        long blockTime = System.currentTimeMillis() - start;
        
        System.gc();
        long memAfterBlock = runtime.totalMemory() - runtime.freeMemory();
        
        System.out.printf("Block-wise: %d ms, Memory used: %d KB%n", 
                        blockTime, (memAfterBlock - memBefore) / 1024);
        
        blockMultiplier.shutdown();
        
        System.out.printf("Block-wise is %.2fx faster and uses %.2fx memory%n%n", 
                        (double) rowTime / blockTime, 
                        (double) (memAfterBlock - memBefore) / (memAfterRow - memBefore));
    }
    
    public static void main(String[] args) {
        System.out.println("=== Question 27: Multi-threaded Matrix Multiplication ===");
        System.out.printf("Available processors: %d%n%n", NUM_THREADS);
        
        testMatrixMultiplication();
        performanceComparison();
        testBlockSizeImpact();
        testScalability();
        testMemoryPerformance();
        
        System.out.println("=== Key Concepts Demonstrated ===");
        System.out.println("• Row-wise parallelization using ExecutorService");
        System.out.println("• Block-wise parallelization for cache efficiency");
        System.out.println("• Fork-Join framework for divide-and-conquer");
        System.out.println("• Strassen's algorithm with parallel execution");
        System.out.println("• Performance comparison of different approaches");
        System.out.println("• Impact of block size on cache performance");
        System.out.println("• Thread scalability analysis");
        System.out.println("• Memory usage optimization in parallel algorithms");
        System.out.println("• Load balancing in matrix computation");
    }
}
