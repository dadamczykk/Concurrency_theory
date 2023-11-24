package lab5.pk2cond;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Producer extends Thread {
    private final Buffer buf;
    private final int M;

    private final int id;

    public int loops;

    public List<List<Long>> timesList;

    Random generator;

    public Producer(Buffer buf, int M, int id, long seed)  {
        this.buf = buf;
        this.M = M;
        this.id = id;
        timesList = new ArrayList<>();
        for (int i = 0; i <= M; i++){
            timesList.add(new ArrayList<>());
        }
        if (seed < 0) generator = new Random();
        else generator = new Random(seed+2);

    }

    public void run() {


        while(true) {
            loops++;
            int val = generator.nextInt(M-1)+1;


            long start = System.nanoTime();
            buf.produce(val, id);
            long elapsed = System.nanoTime() - start;
            timesList.get(val).add(elapsed);

        }
    }
}