package com.hw13_jmm_execution_manager;

public interface ThreadPool {

    /**
     * запускает потоки. Потоки бездействуют, до тех пор пока не появится новое задание в очереди (см. execute)
     */
    void start();

    /**
     * передает потокам на выполнение очередь задач инкапсулированных в объект com.hw13_jmm_execution_manager.ContextImpl
     * Освободившийся поток должен выполнить задание из очереди. Каждое задание должны быть выполнено ровно 1 раз
     * @param context
     */
    void executeContext(Context context);
}
