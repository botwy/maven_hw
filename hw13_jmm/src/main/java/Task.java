import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class Task<T> {

    private final Callable<? extends T> callable;

    private volatile T result;

    public Task(Callable<? extends T> callable) {
       this.callable=callable;
   }


       public T get() {
           if (result == null) {
               synchronized (this) {
                   if (result == null)
                   try {

                       result = callable.call();
                       TimeUnit.SECONDS.sleep(5);
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
