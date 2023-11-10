package lab4.producerconsumerbest;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    private final LinkedList<Integer> buffer;
    private final int bufferCapacity;
    private final ReentrantLock lock;
    private final Condition firstProducer;
    private final Condition firstConsumer;
    private final Condition restProducers;
    private final Condition restConsumers;

    private boolean hasFirstProducer = false;
    private boolean hasFirstConsumer = false;

    private int countFirstProducer = 0;
    private int countFirstConsumer = 0;
    private int countRestProducers = 0;
    private int countRestConsumers = 0;


    public Buffer(int m){
        this.buffer = new LinkedList<>();
        this.bufferCapacity = m * 2;
        this.lock = new ReentrantLock();
        this.firstConsumer = lock.newCondition();
        this.firstProducer = lock.newCondition();
        this.restConsumers = lock.newCondition();
        this.restProducers = lock.newCondition();
    }

    public void produce(int[] toProduce, int id, Producer pr){
        lock.lock();
        try {
            countRestProducers++;
            while(hasFirstProducer){
                pr.loops++;
//                System.out.println("PRODUCER id: " + id +
//                        " waiting on restProducers with " + countRestProducers + " processes");
                restProducers.await();
            }
            countRestProducers--;
            countFirstProducer++;
            hasFirstProducer = true;
            while (buffer.size() + toProduce.length > bufferCapacity){

//                System.out.println("PRODUCER id: " + id +
//                        " waiting on firstProducer with " + countFirstProducer + " processes");

                firstProducer.await();
            }
            countFirstProducer--;

            hasFirstProducer = false;
            for (int j : toProduce) buffer.add(j);

//            System.out.println("PRODUCER id: " + id +
//                    " added " + toProduce.length + " to buffer");

            restProducers.signal();
            firstConsumer.signal();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            lock.unlock();
        }
    }

    public void consume(int toConsume, int id, Consumer cs){
        lock.lock();
        try {
            countRestConsumers++;
            while(hasFirstConsumer){
                cs.loops++;
//                System.out.println("CONSUMER id: " + id +
//                        " waiting on restConsumers with " + countRestConsumers + " processes");

                restConsumers.await();
            }
            countRestConsumers--;
            countFirstConsumer++;
            hasFirstConsumer = true;
            while (buffer.size() - toConsume < 0){
//                cs.loops++;
//                System.out.println("CONSUMER id: " + id +
//                        " waiting on firstConsumer with " + countFirstConsumer + " processes");


                firstConsumer.await();
            }
            countFirstConsumer--;

            hasFirstConsumer = false;
            for (int i=0; i < toConsume; i++) buffer.removeFirst();
//            System.out.println("CONSUMER id: " + id +
//                    " consumed " + toConsume + " from buffer");

            restConsumers.signal();
            firstProducer.signal();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally{
            lock.unlock();
        }
    }
}
