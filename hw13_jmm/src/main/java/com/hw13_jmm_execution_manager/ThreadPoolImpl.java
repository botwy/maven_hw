package com.hw13_jmm_execution_manager;

import java.util.Queue;
import java.util.concurrent.TimeUnit;

//пул потоков, в который передаем объект com.hw13_jmm_execution_manager.ContextImpl
public class ThreadPoolImpl implements ThreadPool {

    private final int num_threads;
    private final ThreadInner[] threads;
    private Queue<Runnable> que;
    private volatile ContextImpl contextImpl;

    private final Object monitor = new Object();
    private volatile boolean flag_finish = false;

    public ThreadPoolImpl(int num_threads) {
        this.num_threads = num_threads;
        threads = new ThreadInner[num_threads];
    }


    @Override
    public void start() {

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new ThreadInner();
            threads[i].start();
        }

    }


    /**
     * выполнение задач инкапсулированных в объект com.hw13_jmm_execution_manager.ContextImpl (передача потокам)
     * запуск отдельного потока, который после выполнения всех задач выполняет callback
     * @param context
     */
    @Override
    public void executeContext(Context context) {
        this.contextImpl = (ContextImpl) context;
        que = contextImpl.getQue();
        synchronized (que) {
            que.notify();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                //  while (!contextImpl.isFinished());
                synchronized (monitor) {
                    while (!flag_finish) {
                        try {
                            monitor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                contextImpl.getCallback().run();
            }
        }).start();

    }

    /**
     * Внутренний класс потока для нашего ThreadPool
     */
    public class ThreadInner extends Thread {
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

                if (contextImpl.isFinished()) {
                    synchronized (monitor) {
                        flag_finish = true;
                        monitor.notify();
                    }
                }
            }
        }
    }

}
