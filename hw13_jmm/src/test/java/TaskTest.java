import org.junit.Test;

import java.sql.Time;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class TaskTest {

    @Test
    public void test() {
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
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
