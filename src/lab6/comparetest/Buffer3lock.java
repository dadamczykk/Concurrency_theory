package lab6.comparetest;

import com.sun.jdi.InternalException;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer3lock implements IBuffer{
    private int buffer = 0;
    public final int bufferCapacity;
    private final ReentrantLock lockGeneral;
    private final ReentrantLock producerLock;
    private final ReentrantLock consumerLock;
    private final Condition generalCond;


    public Buffer3lock(int m){
        this.bufferCapacity = m * 2;
        this.lockGeneral = new ReentrantLock();
        this.producerLock = new ReentrantLock();
        this.consumerLock = new ReentrantLock();
        this.generalCond = lockGeneral.newCondition();
    }

    public void produce(int toProduce, int id){
        producerLock.lock();
        lockGeneral.lock();
        try {
            while(buffer + toProduce > bufferCapacity){
                generalCond.await();
            }
            buffer += toProduce;
            generalCond.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally{
            lockGeneral.unlock();
            producerLock.unlock();
        }
    }

    public void consume(int toConsume, int id){
        consumerLock.lock();
        lockGeneral.lock();
        try {
            while(buffer - toConsume < 0){
                generalCond.await();
            }
            buffer -= toConsume;
            generalCond.signal();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally{
            lockGeneral.unlock();
            consumerLock.unlock();
        }
    }
}
