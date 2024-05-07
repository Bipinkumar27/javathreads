import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

/*
hiking example people start hike once all 5 are ready
 */
public class ThreadWaitExampleCyclicBarrier {
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(5);
        System.out.println("Get ready for hiking !!");

        for(int i=1;i<=5;i++){
            Thread thread = new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName() + " Getting ready");
                    sleep(ThreadLocalRandom.current()
                        .nextInt(300,800));
                    System.out.println(Thread.currentThread().getName() + " Ready");
                    barrier.await();
                }  catch (InterruptedException | BrokenBarrierException e) {
                    throw new RuntimeException(e);
                }
            }, "Friend-"+i);
            thread.start();
        }
        /*try {
            barrier.await();
            System.out.println("lets start the hiking !!");
        } catch (InterruptedException | BrokenBarrierException e) {
            throw new RuntimeException(e);
        }*/
    }

    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}