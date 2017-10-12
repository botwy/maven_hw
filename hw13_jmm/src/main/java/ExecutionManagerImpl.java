import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExecutionManagerImpl implements ExecutionManager {

//это неблокирующий метод, который сразу возвращает объект Context, в который инкапсулированы tasks, callback. Context это интерфейс.
//задачи выполнять будет пул потоков, в который передаем объект Context
    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
       Context context = new ContextImpl(callback,tasks);
       ThreadPool threadPool = new ThreadPoolImpl(2);
       threadPool.start();
       threadPool.executeContext(context);
        return context;
    }



}