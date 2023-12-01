package lab6.comparetest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer2cond implements IBuffer{
    private int buffer = 0;
    public final int bufferCapacity;
    private final ReentrantLock lock;
    private final Condition Producers;
    private final Condition Consumers;


    public Buffer2cond(int m){
        this.bufferCapacity = m * 2;
        this.lock = new ReentrantLock();
        this.Consumers = lock.newCondition();
        this.Producers = lock.newCondition();
    }

    public void produce(int toProduce, int id){
        lock.lock();
        try {
            while(buffer + toProduce > bufferCapacity){
                Producers.await();
            }
            buffer += toProduce;
            Consumers.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally{
            lock.unlock();
        }
    }

    public void consume(int toConsume, int id){
        lock.lock();
        try {
            while(buffer - toConsume < 0){
                Consumers.await();
            }
            buffer -= toConsume;
            Producers.signal();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally{
            lock.unlock();
        }
    }
}
