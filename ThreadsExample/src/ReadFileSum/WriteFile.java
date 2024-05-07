package ReadFileSum;

import java.io.FileWriter;
import java.io.IOException;

public class WriteFile {
    public static void main(String[] args) {
        String filePath = "output.txt"; // Change this to your desired file path

        try {
            FileWriter writer = new FileWriter(filePath);

            for (int i = 0; i < 100000000; i++) {
                writer.write("1\n"); // Writing "1" followed by a newline character
            }

            writer.close();
            System.out.println("File has been written successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}
