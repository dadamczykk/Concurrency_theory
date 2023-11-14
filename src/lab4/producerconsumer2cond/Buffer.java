package lab4.producerconsumer2cond;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final LinkedList<Integer> buffer;
    public final int bufferCapacity;
    private final ReentrantLock lock;
    private final Condition Producers;
    private final Condition Consumers;

    private int countProducers = 0;
    private int countConsumers = 0;


    public Buffer(int m){
        this.buffer = new LinkedList<>();
        this.bufferCapacity = m * 2;
        this.lock = new ReentrantLock();
        this.Consumers = lock.newCondition();
        this.Producers = lock.newCondition();
    }

    public void produce(int[] toProduce, int id){
        lock.lock();
        try {
            countProducers++;
            while(buffer.size() + toProduce.length > bufferCapacity){
                System.out.println("PRODUCER id: " + id +
                          " waiting on Producers | number of waiting processes: " + countProducers);
                Producers.await();
            }
            countProducers--;

            for (int j : toProduce) buffer.add(j);

            System.out.println("PRODUCER id: " + id +
                      " added " + toProduce.length + " to buffer");

            Consumers.signal();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            lock.unlock();
        }
    }

    public void consume(int toConsume, int id){
        lock.lock();
        try {
            countConsumers++;
            while(buffer.size() - toConsume < 0){

                System.out.println("CONSUMER id: " + id +
                          " waiting on Consumers | number of waiting processes: " + countConsumers);

                Consumers.await();
            }
            countConsumers--;

            for (int i=0; i < toConsume; i++) buffer.removeFirst();
            System.out.println("CONSUMER id: " + id +
                      " consumed " + toConsume + " from buffer");

            Producers.signal();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            lock.unlock();
        }
    }
}
