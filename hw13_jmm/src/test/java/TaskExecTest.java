import org.junit.Test;

import java.sql.Time;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class TaskExecTest {

    @Test
    public void testTask() {
        Task<Integer> task = new Task<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int x =10;
                int y = 5;
                return x/y;
            }
        });
        for (int i = 0; i <5 ; i++) {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    System.out.println(Thread.currentThread() + " " + task.get());

                }
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testExecManager() {
        Runnable[] arr_run = new Runnable[5];
        for (int i = 0; i < arr_run.length; i++) {
            arr_run[i]=new Runnable() {
                @Override
                public void run() {

                    System.out.println("runnable"+" "+Thread.currentThread());
                }
            };
        }
        ExecutionManager executionManager = new ExecutionManagerImpl();
     Context context = executionManager.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("callback"+" "+Thread.currentThread());
            }
        }, arr_run);

        try {
            TimeUnit.SECONDS.sleep(2);
            context.interrupt();

            TimeUnit.SECONDS.sleep(5);
            System.out.println("CompletedTaskCount  "+context.getCompletedTaskCount());
            System.out.println("isFinished  "+context.isFinished());
            System.out.println("InterruptedTaskCount  "+context.getInterruptedTaskCount());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
