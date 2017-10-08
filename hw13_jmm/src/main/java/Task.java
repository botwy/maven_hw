import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Task<T> {

    private final Callable<? extends T> callable;

    private volatile T result;
    private volatile boolean isCall = false;
    private final Object lock = new Object();

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }



    public T get() {
        if (result == null) {
            synchronized (lock) {
                while (isCall) {
                    System.out.println(isCall);
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            synchronized (callable) {
                if (result == null)
                    try {

                        isCall = true;
                        TimeUnit.SECONDS.sleep(5);
                        result = callable.call();
                        TimeUnit.SECONDS.sleep(5);
                        isCall = false;
                        callable.notify();
                    } catch (Exception e) {
                        throw new GetResultException(e);
                    }
            }
        }
        return result;
    }


    public static class GetResultException extends RuntimeException {
        public GetResultException() {
            super();
        }

        public GetResultException(Throwable cause) {
            super(cause);
        }
    }
}
