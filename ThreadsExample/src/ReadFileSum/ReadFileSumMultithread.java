package ReadFileSum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadFileSumMultithread {
    private static final int NUM_THREADS = Runtime.getRuntime()
        .availableProcessors(); // Number of available processors

    public static void main(String[] args) {
        String filename = "output.txt";
        AtomicInteger sum = new AtomicInteger(0);
        long startTime = System.nanoTime();

        int numThreads = 4;
        int totalSum = calculateSum(filename, numThreads);
        System.out.println("Total Sum: " + totalSum);

        long endTime = System.nanoTime();

        // Calculate difference
        long executionTime = endTime - startTime;
        double seconds = (double) executionTime / 1_000_000_000.0;
        System.out.println("Total execution time: " + seconds + " seconds");
    }
    public static int calculateSum(String filename, int numThreads) {
        AtomicInteger totalSum = new AtomicInteger(0);
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int number = Integer.parseInt(line);
                executor.execute(() -> totalSum.addAndGet(number));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Wait for all threads to finish
        }

        return totalSum.get();
    }
}