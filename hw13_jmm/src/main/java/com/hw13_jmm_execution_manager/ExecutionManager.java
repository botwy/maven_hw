package com.hw13_jmm_execution_manager;

/**
 * execute принимает массив тасков, это задания которые com.hw13_jmm_execution_manager.ExecutionManager должен выполнять
 * параллельно (в своем пуле потоков).
 * После завершения всех тасков должен выполниться callback (ровно 1 раз).
 * Метод execute – это неблокирующий метод, который сразу возвращает объект com.hw13_jmm_execution_manager.Context
 */
public interface ExecutionManager {
 Context execute(Runnable callback, Runnable... tasks);
}