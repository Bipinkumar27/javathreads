public class VolatileExample {
    private static volatile boolean flag = false;
    public static void main(String[] args) {

        Thread t1 = new Thread(()->{
            System.out.println(Thread.currentThread().getName() + " is processing");
            try {
                Thread.sleep(300);
                flag = true;
                System.out.println(Thread.currentThread().getName() + " Finished");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "T1");

        Thread t2 = new Thread(()->{
            while(!flag){

            }
            System.out.println(Thread.currentThread().getName() + " is processing");
            try {
                Thread.sleep(300);
                System.out.println(Thread.currentThread().getName() + " Finished");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "T2");

        t1.start();
        t2.start();
    }
}
