package lab6.comparetest;

import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.random.RandomGenerator;

public class Consumer extends Thread {

    private IBuffer buf;
    private final int M;
    private final int id;
    public long loops;
    public List<List<Long>> timesList;
    RandomGenerator generator;
    public Consumer(IBuffer buf, int M, int id, ERandom randType, int seed){
        this.buf = buf;
        this.M = M;
        this.id = id;
        timesList = new ArrayList<>();
        for (int i = 0; i <= M; i++){
            timesList.add(new ArrayList<>());
        }
        this.generator = switch (randType){
            case secure -> new SecureRandom(ByteBuffer.allocate(4).putInt(seed+id).array());
            case standard -> new Random(seed+id);
            case thread -> ThreadLocalRandom.current();

        };
    }

    public void run(){
        while(!Thread.currentThread().isInterrupted()) {
            long start = System.nanoTime();
            loops++;
            int val = generator.nextInt(M-1)+1;
            buf.consume(val, id);
            long elapsed = System.nanoTime() - start;
            timesList.get(val).add(elapsed);

        }
    }

}
