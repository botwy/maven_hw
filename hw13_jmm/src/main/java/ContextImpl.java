import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class ContextImpl implements Context {
   public int completeTask;
   public int failedTask;
   public int interruptedTask;

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
            System.out.println(getCompletedTaskCount());
            System.out.println(getInterruptedTaskCount());
           if(que.size()==0 && count==completeTask+interruptedTask) return true;
           else return false;
        }
    }
}