package com.hw11_12_thread;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

/**
 *  в конструкторе задается минимальное и максимальное(int min, int max) число потоков,
 *  количество запущенных потоков может быть увеличено от минимального к максимальному,
 *  если при добавлении нового задания в очередь нет свободного потока для исполнения этого задания.
 *  При отсутствии задания в очереди, количество потоков опять должно быть уменьшено до значения min
 */
public class ScalableIThreadPool implements IThreadPool {
    private final int min_num_threads;
    private final int max_num_threads;
    private final List<ThreadInner> threads;
    private final Queue<Runnable> que;
    private List<ThreadInner> all_threads = new ArrayList<>();

    public ScalableIThreadPool(int min_num_threads, int max_num_threads) {
        this.min_num_threads = min_num_threads;
        this.max_num_threads = max_num_threads;
        threads = new ArrayList<>();
        que = new ArrayDeque<>();

    }

    public Queue<Runnable> getQue() {
        return que;
    }

    public List<ThreadInner> getThreads() {
        return threads;
    }

    public void reduceThread(ThreadInner thread) {
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
           threads.add(new ThreadInner());
           all_threads.add(threads.get(i));
            threads.get(i).start();

        }

    }

    @Override
    public void execute(Runnable runnable) {
        synchronized (que) {
            que.add(runnable);
            for (ThreadInner thread:threads) {
                if (thread.getState()== Thread.State.WAITING) {
                    que.notify();
                    return;
                }
            }
            if (threads.size()<max_num_threads) {
                threads.add(new ThreadInner());
                all_threads.add(threads.get(threads.size()-1));
                threads.get(threads.size()-1).start();
            }
           que.notify();
        }
    }

    /**
     * Внутренний класс потока для нашего ThreadPool
     */
   private class ThreadInner extends Thread {
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
