package ReadFileSum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ReadFileSumSingleThread {
    public static void main(String[] args) {

        String filename = "output.txt";
        int sum = 0;
        long startTime = System.nanoTime();

        System.currentTimeMillis();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    sum += Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    // Handle non-integer values in the file
                    System.err.println("Error parsing integer: " + e.getMessage());
                    // You can choose to continue processing or exit based on your requirement
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            System.exit(2);
        }
        System.out.println("The sum of integers in the file is: " + sum);

        long endTime = System.nanoTime();

        // Calculate difference
        long executionTime = endTime - startTime;
        double seconds = (double)executionTime / 1_000_000_000.0;
        System.out.println("Total execution time: " + seconds + " seconds");
    }

}
