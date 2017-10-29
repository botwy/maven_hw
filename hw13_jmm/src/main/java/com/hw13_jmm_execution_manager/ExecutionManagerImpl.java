package com.hw13_jmm_execution_manager;

/**
 * execute это неблокирующий метод, который сразу возвращает объект com.hw13_jmm_execution_manager.Context,
 * в который инкапсулированы tasks, callback. com.hw13_jmm_execution_manager.Context это интерфейс.
 * задачи выполнять будет пул потоков, в который передаем объект com.hw13_jmm_execution_manager.Context
 */
public class ExecutionManagerImpl implements ExecutionManager {

    @Override
    public Context execute(Runnable callback, Runnable... tasks) {
       Context context = new ContextImpl(callback,tasks);
       ThreadPool threadPool = new ThreadPoolImpl(2);
       threadPool.start();
       threadPool.executeContext(context);
        return context;
    }



}
