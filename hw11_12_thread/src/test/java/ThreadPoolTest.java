import com.hw11_12_thread.FixedIThreadPool;
import com.hw11_12_thread.IThreadPool;
import com.hw11_12_thread.ScalableIThreadPool;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    @Test
    public void fixedThreadPool(){
        IThreadPool IThreadPool = new FixedIThreadPool(2);
        IThreadPool.start();

        for (int i=0;i<5;i++) {
            IThreadPool.execute(new Runnable() {
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
        for (Runnable runnable:((FixedIThreadPool) IThreadPool).getQue()
             ) {
            System.out.println(runnable);
        }
    }

    @Test
    public void scalableThreadPool(){
        IThreadPool IThreadPool = new ScalableIThreadPool(2,5);
        IThreadPool.start();

        for (int i=0;i<5;i++) {
            IThreadPool.execute(new Runnable() {
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
        for (Runnable runnable:((ScalableIThreadPool) IThreadPool).getQue()
                ) {
            System.out.println(runnable);
        }
        System.out.println(((ScalableIThreadPool) IThreadPool).getThreads().size());

        for (Thread thread:((ScalableIThreadPool) IThreadPool).getThreads()
             ) {
            System.out.println(thread+" "+thread.getState());
        }
    }
}
