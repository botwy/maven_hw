import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    @Test
    public void fixedThreadPool(){
        ThreadPool threadPool = new FixedThreadPool(2);
        threadPool.start();

        for (int i=0;i<5;i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread());
                }
            });
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Runnable runnable:((FixedThreadPool)threadPool).getQue()
             ) {
            System.out.println(runnable);
        }
    }

    @Test
    public void scalableThreadPool(){
        ThreadPool threadPool = new ScalableThreadPool(2,5);
        threadPool.start();

        for (int i=0;i<5;i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread());
                }
            });
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Runnable runnable:((ScalableThreadPool)threadPool).getQue()
                ) {
            System.out.println(runnable);
        }
        System.out.println(((ScalableThreadPool) threadPool).getThreads().size());

        for (ScalableThreadPool.MyThread thread:((ScalableThreadPool) threadPool).all_threads
             ) {
            System.out.println(thread+" "+thread.getState());
        }
    }
}
