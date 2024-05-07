import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CallableAndFutureDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        Callable<String> callable = () ->{
            //processing
            Thread.sleep(1000);
            return "Result returned from: " + Thread.currentThread().getName();
        };

        System.out.println("submitting callable");
        Future<String> future = executorService.submit(callable);

        // Do some other work
        System.out.println("processing something else while callable is getting executed in parallel");

        //Blocking the further execution waiting for callable result available
        try {
            //String result = (String)future.get(500, TimeUnit.MICROSECONDS);
            String result = (String)future.get();
            System.out.println("returned result from callable: "+ result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        } /*catch (TimeoutException e) {
            throw new RuntimeException(e);
        }*/
        executorService.shutdown();
    }
}
