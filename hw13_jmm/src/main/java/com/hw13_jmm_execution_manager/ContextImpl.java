package com.hw13_jmm_execution_manager;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class ContextImpl implements Context {
   public volatile int completeTask;
   public volatile int failedTask;
   public volatile int interruptedTask;

    private final Runnable callback;
    private final Queue<Runnable> que;
    private final int count;

    public ContextImpl(Runnable callback, Runnable... tasks) {
        this.callback = callback;
        this.que = new ArrayDeque<Runnable>(Arrays.asList(tasks));
        this.count = tasks.length;
    }

    public Runnable getCallback() {
        return callback;
    }

    public Queue<Runnable> getQue() {
        return que;
    }



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
        synchronized (que) {
            interruptedTask=que.size();
            que.clear();
        }
    }

    @Override
    public boolean isFinished() {
        synchronized (que) {
           if(que.size()==0 && count==completeTask+interruptedTask) return true;
           else return false;
        }
    }
}