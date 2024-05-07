public class OddEvenPrint {
    public static void main(String[] args) {
        Printer p1 = new Printer(10,1);
        Printer p2 = new Printer(10,0);

        Thread t1 = new Thread(p1,"ODD");
        Thread t2 = new Thread(p2, "Even");

        t1.start();
        t2.start();
    }
}

class Printer implements Runnable {
    private static int number =1;
    private int remainder;
    private int max;
    private static Object lock = new Object();

    public Printer(int max, int remainder) {
        this.remainder = remainder;
        this.max = max;
    }

    @Override
    public void run() {
        while (number < max) {
            synchronized (lock) {
                while (number % 2 != remainder) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                System.out.println(Thread.currentThread().getName() + " : " + number);
                number++;
                lock.notify();
            }
        }
    }

}