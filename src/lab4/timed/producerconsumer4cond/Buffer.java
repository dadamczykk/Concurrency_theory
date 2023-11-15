package lab4.timed.producerconsumer4cond;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final LinkedList<Integer> buffer;
    public final int bufferCapacity;
    private final ReentrantLock lock;
    private final Condition firstProducer;
    private final Condition firstConsumer;
    private final Condition restProducers;
    private final Condition restConsumers;

    private int countFirstProducer = 0;
    private int countFirstConsumer = 0;
    private int countRestProducers = 0;
    private int countRestConsumers = 0;
    boolean hasFirstProducer = false;
    boolean hasFirstConsumer = false;

    public Buffer(int m){
        this.buffer = new LinkedList<>();
        this.bufferCapacity = m * 2;
        this.lock = new ReentrantLock();
        this.firstConsumer = lock.newCondition();
        this.firstProducer = lock.newCondition();
        this.restConsumers = lock.newCondition();
        this.restProducers = lock.newCondition();
    }

    public void produce(int[] toProduce, int id){
        lock.lock();
        try {
            countRestProducers++;
            while(hasFirstProducer){

//                  System.out.println("PRODUCER id: " + id +
//                          " waiting on restProducers | number of waiting processes: " + countRestProducers);
                restProducers.await();
            }
            countRestProducers--;
            countFirstProducer++;
            hasFirstProducer = true;
            while (buffer.size() + toProduce.length > bufferCapacity){

//                  System.out.println("PRODUCER id: " + id +
//                          " waiting on firstProducer | number of waiting processes: " + countFirstProducer);

                firstProducer.await();
            }
            countFirstProducer--;
            hasFirstProducer = false;

            for (int j : toProduce) buffer.add(j);

//            System.out.println("PRODUCER id: " + id +
//                      " added " + toProduce.length + " to buffer");

            restProducers.signal();
            firstConsumer.signal();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            lock.unlock();
        }
    }

    public void consume(int toConsume, int id){
        lock.lock();
        try {
            countRestConsumers++;
            while(hasFirstConsumer){

//                  System.out.println("CONSUMER id: " + id +
//                          " waiting on restConsumers | number of waiting processes: " + countRestConsumers);

                restConsumers.await();
            }
            countRestConsumers--;
            countFirstConsumer++;
            hasFirstConsumer = true;
            while (buffer.size() - toConsume < 0){
//                  System.out.println("CONSUMER id: " + id +
//                          " waiting on firstConsumer | number of waiting processes: " + countFirstConsumer);


                firstConsumer.await();
            }
            countFirstConsumer--;
            hasFirstConsumer = false;

            for (int i=0; i < toConsume; i++) buffer.removeFirst();
//            System.out.println("CONSUMER id: " + id +
//                      " consumed " + toConsume + " from buffer");

            restConsumers.signal();
            firstProducer.signal();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            lock.unlock();
        }
    }
}
