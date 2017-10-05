import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class ExecutionManagerImpl implements ExecutionManager{
    private final ContextImpl context;

    private final int num_threads;
    private final MyThread[] threads;
    private final Queue<Runnable> que;

    public ExecutionManagerImpl(int num_threads) {
        this.num_threads = num_threads;
        threads = new MyThread[num_threads];
        que = new ArrayDeque<>();
        context=new ContextImpl();


    }
    public void start() {

        for (int i = 0; i <threads.length ; i++) {
            threads[i] = new MyThread();
            threads[i].start();
        }

    }

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
        que.addAll(Arrays.asList(tasks));
        start();
        return context;
    }

    public class ContextImpl implements Context {
int completeTask;
int failedTask;
int interruptedTask;
boolean isFinished;

        @Override
        public int getCompletedTaskCount() {
            return completeTask;
        }

        @Override
        public int getFailedTaskCount() {
            return failedTask;
        }

        @Override
        public int getInterruptedTaskCount() {
            return interruptedTask;
        }

        @Override
        public void interrupt() {

        }

        @Override
        public boolean isFinished() {
            return isFinished;
        }
    }

    public class MyThread extends Thread {
        @Override
        public void run() {
            Runnable runnable;
                    while (!que.isEmpty()) {
                        synchronized (que) {
                            runnable = que.poll();
                        }
                        try {
                            runnable.run();
                            TimeUnit.SECONDS.sleep(1);
                            context.completeTask=context.completeTask+1;
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

        }
    }
}
