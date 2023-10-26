package lab2.homework.consumerproducer2m;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Buffer {
    private final Queue<Integer> queue;
    private final int M;
    private int occupied = 0;
    private final Lock lock;
    private final Condition consumer;
    private final Condition producer;


    public Buffer(int M) {
        this.M = M;
        this.queue = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.producer = lock.newCondition();
        this.consumer = lock.newCondition();
    }

    public void produce(int howManyItems) throws InterruptedException {
        lock.lock();
        try {
            while (2*M - occupied < howManyItems) {
                producer.await();
            }
            for(int i = 0; i < howManyItems; i++){
                queue.add(i);
            }
            occupied += howManyItems;
            System.out.println("Produced: " + howManyItems);
            producer.signal();
            consumer.signal();

        } finally {
            lock.unlock();
        }
    }

    public int consume(int howManyItems) throws InterruptedException {
        lock.lock();
        try {
            while (howManyItems > occupied) {
                consumer.await();
            }
            for (int i = 0; i < howManyItems; i++){
                int item = queue.poll();
            }
            occupied -= howManyItems;
            System.out.println("Consumed: " + howManyItems);
            producer.signal();
            consumer.signal();
            return howManyItems;
        } finally {
            lock.unlock();
        }
    }
}
