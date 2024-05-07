import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/*
hiking example people start hike once all 5 are ready
 */
public class ThreadWaitExampleCountDownLatch {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(5);
        System.out.println("Get ready for hiking !!");

        for(int i=1;i<=5;i++){
            Thread thread = new Thread(()->{
                System.out.println(Thread.currentThread().getName() + " Getting ready");
                  sleep(ThreadLocalRandom.current()
                      .nextInt(300,600));
                System.out.println(Thread.currentThread().getName() + " Ready");
                latch.countDown();
            }, "Friend-"+i);
            thread.start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("lets start the hiking !!");
    }

    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}