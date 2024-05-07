import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class ForkJoinDemo {
    public static void main(String[] args) {
        int [] tasks = new int[20];
        ForkJoinPool executor = ForkJoinPool.commonPool();
       // ForkJoinPool executor = new ForkJoinPool(5);
        EssayTask submitTasks = new EssayTask(tasks,0,tasks.length);
        System.out.println(executor.invoke(submitTasks));
    }
}

class EssayTask extends RecursiveTask {
    public static final int THRESHOLD = 10;
    private int[] tasks;
    private int start;
    private int end;

    public EssayTask(int[] tasks,int start, int end){
        this.tasks = tasks;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long finishedSubTopics = 0;

        if(end-start <= THRESHOLD){
            for(int i=start; i<end;i++){
                processing();
                finishedSubTopics++;
                System.out.println("Subsection: " + (i+1)+ " completed by: " + Thread.currentThread().getName());
            }
            return finishedSubTopics;
        }
        else{
            int mid = (end-start)/2;
            EssayTask leftTask = new EssayTask(tasks, start, mid);
            EssayTask rightTask = new EssayTask(tasks, mid, end);
            leftTask.fork();
            rightTask.fork();
            return (long) leftTask.join() + (long) rightTask.join();
        }
    }

    public void processing(){
        try {
            Thread.sleep(ThreadLocalRandom.current()
                .nextInt(100,300));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
