package lab4.timed.producerconsumer2cond;

import java.util.ArrayList;
import java.util.List;

public class Consumer extends Thread {

    private Buffer buf;
    private final int M;
    private final int id;
    public int loops;
    public List<List<Long>> timesList;

    public Consumer(Buffer buf, int M, int id){
        this.buf = buf;
        this.M = M;
        this.id = id;
        timesList = new ArrayList<>();
        for (int i = 0; i <= M; i++){
            timesList.add(new ArrayList<>());
        }
    }

    public void run(){
        while(true) {
            loops++;

            int val = (int) (Math.random()*(M-1)+1);
            long start = System.nanoTime();
            buf.consume(val, id);
            long elapsed = System.nanoTime() - start;
            timesList.get(val).add(elapsed);

        }
    }
}
