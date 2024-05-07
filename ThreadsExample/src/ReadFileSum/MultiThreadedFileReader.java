package ReadFileSum;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiThreadedFileReader {
    private static final int NUM_THREADS = 4; // Number of threads to use

    public static void main(String[] args) {
        String filename = "output.txt"; // File to read

        try {
            AtomicInteger lineCount = new AtomicInteger(0); // Atomic integer to keep track of line count
            ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS); // Create thread pool

            // Calculate chunk size
            long fileSize = getFileSize(filename);
            long chunkSize = (fileSize + NUM_THREADS - 1) / NUM_THREADS; // Round up to ensure all lines are read

            // Create and submit tasks to read file
            for (int i = 0; i < NUM_THREADS; i++) {
                long startLine = i * chunkSize;
                long endLine = Math.min(startLine + chunkSize, fileSize);
                executor.execute(new FileReaderTask(filename, startLine, endLine, lineCount));
            }
            System.out.println(lineCount.get());
            // Shutdown executor
            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long getFileSize(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            long lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            return lines;
        }
    }

    static class FileReaderTask implements Runnable {
        private final String filename;
        private final long startLine;
        private final long endLine;
        private final AtomicInteger lineCount;

        FileReaderTask(String filename, long startLine, long endLine, AtomicInteger lineCount) {
            this.filename = filename;
            this.startLine = startLine;
            this.endLine = endLine;
            this.lineCount = lineCount;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                long currentLine = 0;
                String line;

                // Skip lines until startLine
                while (currentLine < startLine && reader.readLine() != null) {
                    currentLine++;
                }

                // Read lines within the range
                while (currentLine < endLine && (line = reader.readLine()) != null) {
                    // Process the line (e.g., store it, parse it, etc.)
                    //System.out.println(Thread.currentThread().getName() + ": " + line);
                    currentLine++;
                }

                // Update line count
                lineCount.addAndGet((int) (currentLine - startLine));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}