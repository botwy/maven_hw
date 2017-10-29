package com.hw11_12_thread;

public interface IThreadPool {

    /**
     * запускает потоки. Потоки бездействуют, до тех пор пока не появится новое задание в очереди (см. execute)
     */
    void start();

    /**
     * cкладывает задание в очередь.
     * Освободившийся поток должен выполнить это задание. Каждое задание должны быть выполнено ровно 1 раз
     * @param runnable
     */
    void execute (Runnable runnable);
}
