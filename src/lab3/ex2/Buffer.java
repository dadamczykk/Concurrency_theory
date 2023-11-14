package lab3.ex2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final int bufferSize;
    private int messagesInBuffer;

    private final Lock lock;
    private final Condition consumer;
    private final Condition producer;

    public Buffer(int bufferSize){
        this.bufferSize = bufferSize;
        this.lock = new ReentrantLock();
        this.consumer = lock.newCondition();
        this.producer = lock.newCondition();
        this.messagesInBuffer = 0;
    }

    public void put(int toPut, Producer pr) {
        lock.lock();
        try {
            while (messagesInBuffer + toPut > bufferSize) {
                try {

                    producer.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            pr.waitLoops += 1;
            this.messagesInBuffer += toPut;
            consumer.signal();
        } finally {
            lock.unlock();
        }

    }

    public synchronized int take(int toTake, Consumer co) {
        lock.lock();
        try{
            while (messagesInBuffer - toTake < 0) {
                try {

                    consumer.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            co.waitLoops += 1;
            messagesInBuffer -= toTake;
            producer.signal();
            return toTake;
        } finally {
            lock.unlock();
        }

    }
}
