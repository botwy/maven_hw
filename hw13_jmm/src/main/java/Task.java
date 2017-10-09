import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Task<T> {

    private final Callable<? extends T> callable;

    private volatile T result;
    private static boolean mustWait = false;
    private static final Object monitor = new Object();

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }



    public T get() {
        if (result == null) {

            synchronized (monitor) {
                while (mustWait) {
               //     System.out.println(isCall);
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
             mustWait=true;
            }

            synchronized (callable) {
                if (result == null)
                    try {
                        result = callable.call();
                    } catch (Exception e) {
                        throw new GetResultException(e);
                    }
            }

            if (mustWait) {
                synchronized (monitor) {
                   mustWait=false;
                   monitor.notify();
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
