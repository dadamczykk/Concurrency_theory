package lab4.timed.producerconsumer2cond;

import java.util.ArrayList;
import java.util.List;

public class Producer extends Thread {
    private final Buffer buf;
    private final int M;

    private final int id;

    public int loops;

    public List<List<Long>> timesList;

    public Producer(Buffer buf, int M, int id)  {
        this.buf = buf;
        this.M = M;
        this.id = id;
        timesList = new ArrayList<>();
        for (int i = 0; i <= M; i++){
            timesList.add(new ArrayList<>());
        }
    }

    public void run() {


        while(true) {
            loops++;
            int val;

            val = (int) (Math.random()*(M-1)+1);

            int[] a = new int[val];
            for(int i=0; i<a.length; i++) {
                a[i] = (int)(Math.random() * 10) + 1;
            }
            long start = System.nanoTime();
            buf.produce(a, id);
            long elapsed = System.nanoTime() - start;
            timesList.get(val).add(elapsed);

        }
    }
}