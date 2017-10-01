import java.util.*;
import java.util.concurrent.TimeUnit;

public class FixedThreadPool implements ThreadPool{

    private final int num_threads;
    private final MyThread[] threads;
    private final Queue<Runnable> que;

    public FixedThreadPool(int num_threads) {
        this.num_threads = num_threads;
        threads = new MyThread[num_threads];
        que = new ArrayDeque<>();

    }

    public Queue<Runnable> getQue() {
        return que;
    }

    @Override
    public void start() {

        for (int i = 0; i <threads.length ; i++) {
            threads[i] = new MyThread();
            threads[i].start();
        }

    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (que) {
            que.add(runnable);
            que.notify();
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            Runnable runnable;

            while (true) {
                synchronized (que) {
                    while (que.isEmpty()) {
                        try {
                            que.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    runnable = que.poll();
                }
                try {
                    runnable.run();
                    TimeUnit.SECONDS.sleep(1);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
