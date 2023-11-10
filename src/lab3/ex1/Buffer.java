package lab3.ex1;

import lab2.ex1.Message;

import java.util.ArrayList;
import java.util.List;
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
    }

    public void put() {
        lock.lock();
        try {
            while (messagesInBuffer == bufferSize) {
                try {
                    producer.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            this.messagesInBuffer++;
            consumer.signal();
        } finally {
            lock.unlock();
        }

    }

    public synchronized int take() {
        lock.lock();
        try{
            while (messagesInBuffer == 0) {
                try {
                    consumer.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            messagesInBuffer--;
            producer.signal();
            return messagesInBuffer;
        } finally {
            lock.unlock();
        }

    }
}
