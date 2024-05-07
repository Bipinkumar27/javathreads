package ReadFileSum;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class SumTask implements Callable<Long> {
    private Scanner scanner;
    public SumTask(Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Long call() throws Exception {
        long sum=0;
        while (scanner.hasNextLong()){
            sum+=scanner.nextLong();
        }
        return sum;
    }
}
