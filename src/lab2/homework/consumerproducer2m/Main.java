package lab2.homework.consumerproducer2m;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int M = 10;
        Buffer buffer = new Buffer(2 * M);
        int numProducers = 5;
        int numConsumers = 5;
        Random random = new Random();
        Thread[] producerThreads = new Thread[numProducers];
        Thread[] consumerThreads = new Thread[numConsumers];

        // Create and start producer threads
        for (;;) {
            final int i = 0;
            final int producerId = i;
            producerThreads[i] = new Thread(() -> {
                try {
                    for (int j = 1; j <= 10; j++) {

                        buffer.produce(random.nextInt(M-1)+1);
                        System.out.println("Producer " + producerId + " produced: " + j);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });

            final int consumerId = i;
            consumerThreads[i] = new Thread(() -> {
                try {
                    for (int j = 1; j <= 10; j++) {
                        int item = buffer.consume(random.nextInt(M-1)+1);
                        System.out.println("Consumer " + consumerId + " consumed: " + item);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            consumerThreads[i].start();

            producerThreads[i].start();


        }

        // Create and start consumer threads
//        for (int i = 0; i < numConsumers; i++) {
//            final int consumerId = i;
//            consumerThreads[i] = new Thread(() -> {
//                try {
//                    for (int j = 1; j <= 10; j++) {
//                        int item = buffer.consume(random.nextInt(M-1)+1);
//                        System.out.println("Consumer " + consumerId + " consumed: " + item);
//                    }
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            });
//            consumerThreads[i].start();
//        }


        // Join all producer threads
//        for (int i = 0; i < numProducers; i++) {
//            try {
//                producerThreads[i].join();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//
//        // Join all consumer threads
//        for (int i = 0; i < numConsumers; i++) {
//            try {
//                consumerThreads[i].join();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
    }
}