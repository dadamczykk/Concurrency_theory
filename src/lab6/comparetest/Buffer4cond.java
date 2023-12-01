package lab6.comparetest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer4cond implements IBuffer{
    private int buffer = 0;
    public final int bufferCapacity;
    private final ReentrantLock lock;
    private final Condition firstProducer;
    private final Condition firstConsumer;
    private final Condition restProducers;
    private final Condition restConsumers;
    boolean hasFirstProducer = false;
    boolean hasFirstConsumer = false;

    public Buffer4cond(int m){
        this.buffer = 0;
        this.bufferCapacity = m * 2;
        this.lock = new ReentrantLock();
        this.firstConsumer = lock.newCondition();
        this.firstProducer = lock.newCondition();
        this.restConsumers = lock.newCondition();
        this.restProducers = lock.newCondition();
    }

    public void produce(int toProduce, int id){
        lock.lock();
        try {
            while(hasFirstProducer){
                restProducers.await();
            }
            hasFirstProducer = true;
            while (buffer + toProduce > bufferCapacity){
                firstProducer.await();
            }
            hasFirstProducer = false;

            buffer += toProduce;

            restProducers.signal();
            firstConsumer.signal();


        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally{
            lock.unlock();
        }
    }

    public void consume(int toConsume, int id){
        lock.lock();
        try {
            while(hasFirstConsumer){
                restConsumers.await();
            }
            hasFirstConsumer = true;
            while (buffer - toConsume < 0){

                firstConsumer.await();
            }
            hasFirstConsumer = false;

            buffer -= toConsume;

            restConsumers.signal();
            firstProducer.signal();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally{
            lock.unlock();
        }
    }
}
