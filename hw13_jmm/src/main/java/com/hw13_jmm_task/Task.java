package com.hw13_jmm_task;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Данный класс в конструкторе принимает экземпляр java.util.concurrent.Callable.
 * Callable похож на Runnuble, но результатом его работы является объект (а не void)
 *
 * @param <T>
 */
public class Task<T> {

    private final Callable<? extends T> callable;

    private volatile T result;
    private volatile Exception exception = null;
    private static boolean mustWait = false;
    private static final Object monitor = new Object();

    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }


    /**
     * возвращает результат работы Callable
     * Выполнение callable начинает тот поток, который первый вызвал метод get().
     * Если несколько потоков одновременно вызывают этот метод, то выполнение начинается только в одном потоке,
     * а остальные ожидают конца выполнения (не нагружая процессор), на условном объекте monitor вызывается wait().
     * Если при вызове get() результат уже просчитан, то он должен вернуться сразу, (даже без задержек на вход
     * в синхронизированную область)
     *
     * @return
     * @throws GetResultException Если при просчете результата произошел Exception,
     *                            то всем потокам при вызове get(), кидается этот Exception, обернутый в GetResultException
     */
    public T get() {
        if (exception != null)
            throw new GetResultException(exception);

        if (result == null) {

            synchronized (monitor) {
                while (mustWait) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                mustWait = true;
            }

            synchronized (callable) {
                if (result == null)
                    try {
                        result = callable.call();
                    } catch (Exception e) {
                        exception = e;
                        throw new GetResultException(e);
                    }
            }

            if (mustWait) {
                synchronized (monitor) {
                    mustWait = false;
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
