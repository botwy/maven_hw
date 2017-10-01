import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class ScalableThreadPool implements ThreadPool{
    private final int min_num_threads;
    private final int max_num_threads;
    private final List<MyThread> threads;
    private final Queue<Runnable> que;
    public List<MyThread> all_threads = new ArrayList<>();

    public ScalableThreadPool(int min_num_threads,int max_num_threads) {
        this.min_num_threads = min_num_threads;
        this.max_num_threads = max_num_threads;
        threads = new ArrayList<>();
        que = new ArrayDeque<>();

    }

    public Queue<Runnable> getQue() {
        return que;
    }

    public List<MyThread> getThreads() {
        return threads;
    }

    public void reduceThread(MyThread thread) {
        synchronized (que) {
            if (que.size()==0 && threads.size()>min_num_threads) {
                threads.remove(thread);
                thread.end();


            }
        }

    }

    @Override
    public void start() {

        for (int i = 0; i <min_num_threads; i++) {
           threads.add(new MyThread());
           all_threads.add(threads.get(i));
            threads.get(i).start();

        }

    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (que) {
            que.add(runnable);
            for (MyThread thread:threads) {
                if (thread.getState()== Thread.State.WAITING) {
                    que.notify();
                    return;
                }
            }
            if (threads.size()<max_num_threads) {
                threads.add(new MyThread());
                all_threads.add(threads.get(threads.size()-1));
                threads.get(threads.size()-1).start();
            }
           que.notify();
        }
    }

    public class MyThread extends Thread {
        private volatile boolean flag = true;

        public void end(){
            flag=false;
        }

        @Override
        public void run() {
            Runnable runnable;

            while (flag) {
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

                reduceThread(this);

           }
        }
    }
}
