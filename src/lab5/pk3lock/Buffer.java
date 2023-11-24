package lab5.pk3lock;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private int buffer = 0;
    public final int bufferCapacity;
    private final ReentrantLock lockGeneral;
    private final ReentrantLock producerLock;
    private final ReentrantLock consumerLock;
    private final Condition generalCond;
//    private final Condition Consumers;

    private int countProducers = 0;
    private int countConsumers = 0;


    public Buffer(int m){
        this.bufferCapacity = m * 2;
        this.lockGeneral = new ReentrantLock();
        this.producerLock = new ReentrantLock();
        this.consumerLock = new ReentrantLock();
        this.generalCond = lockGeneral.newCondition();
//        this.Consumers = lock.newCondition();
//        this.Producers = lock.newCondition();
    }

    public void produce(int toProduce, int id){
        producerLock.lock();
//        lock.lock();
        lockGeneral.lock();
        try {

//            countProducers++;
            while(buffer + toProduce > bufferCapacity){
//                System.out.println("PRODUCER id: " + id +
//                          " waiting on Producers | number of waiting processes: " + countProducers);
//                Producers.await();
                generalCond.await();
            }
//            countProducers--;

            buffer += toProduce;

//            System.out.println("PRODUCER id: " + id +
//                      " added " + toProduce.length + " to buffer");

//            Consumers.signal();
            generalCond.signal();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
//            lock.unlock();
            lockGeneral.unlock();
            producerLock.unlock();
        }
    }

    public void consume(int toConsume, int id){
        consumerLock.lock();
        lockGeneral.lock();
//        lock.lock();
        try {
            countConsumers++;
            while(buffer - toConsume < 0){

//                System.out.println("CONSUMER id: " + id +
//                          " waiting on Consumers | number of waiting processes: " + countConsumers);

//                Consumers.await();
                generalCond.await();
            }
            countConsumers--;

//            for (int i=0; i < toConsume; i++) buffer.removeFirst();
            buffer -= toConsume;
//            System.out.println("CONSUMER id: " + id +
//                      " consumed " + toConsume + " from buffer");

//            Producers.signal();
            generalCond.signal();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
//            lock.unlock();
            lockGeneral.unlock();
            consumerLock.unlock();
        }
    }
}
