import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class FixedThreadPool implements ThreadPool{

    private int num_threads;
    private List<Thread> list_thr;
    private Queue<Runnable> que;

    public FixedThreadPool(int num_threads) {
        this.num_threads = num_threads;
        list_thr = new ArrayList<>();
        que = new PriorityQueue<>();
    }

    @Override
    public void start() {

        for (int i = 0; i < num_threads; i++) {
            list_thr.add(new Thread());
        }

    }

    @Override
    public void execute(Runnable runnable) {
        que.add(runnable);
        list_thr.get(0).start();
    }
}
