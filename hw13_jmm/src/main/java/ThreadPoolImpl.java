import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class ThreadPoolImpl implements ThreadPool{

    private final int num_threads;
    private final MyThread[] threads;
    private Queue<Runnable> que;
    private volatile ContextImpl contextImpl;

    public ThreadPoolImpl(int num_threads) {
        this.num_threads = num_threads;
        threads = new MyThread[num_threads];
    }


    @Override
    public void start() {

        for (int i = 0; i <threads.length ; i++) {
            threads[i] = new MyThread();
            threads[i].start();
        }

    }

    @Override
    public void executeContext (Context context) {
        this.contextImpl=(ContextImpl)context;
        que = contextImpl.getQue();
        synchronized (que) {
            que.notify();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!contextImpl.isFinished());

                contextImpl.getCallback().run();
            }
        });
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            Runnable runnable;

            while (true) {
                if (contextImpl.isFinished())
                    que.add(contextImpl.getCallback());

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
                    synchronized (contextImpl) {
                        contextImpl.completeTask++;
                    }
                } catch (RuntimeException e) {
                    synchronized (contextImpl) {
                        contextImpl.failedTask++;
                    }
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
