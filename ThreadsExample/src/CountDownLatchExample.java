import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);

        Thread t1 = new Thread(()->{
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(400);
                    System.out.println("System setup complete :" + (i+1)*10+ "%");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            latch.countDown();
            System.out.println("System setup done");
        });

        Thread t2 = new Thread(()->{
            try {
                //t1.join();
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(200);
                    System.out.println("T2: processing :" + (i+1)*10+ "%");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("T2 process completed");
        });

        Thread t3 = new Thread(()->{
            try {
                //t1.join();
                latch.await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            for(int i=0;i<10;i++){
                try {
                    Thread.sleep(200);
                    System.out.println("T3: processing :" + (i+1)*10+ "%");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("T3 process completed");
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
