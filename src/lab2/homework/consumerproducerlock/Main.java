package lab2.homework.consumerproducerlock;

public class Main {
    public static void main(String[] args) {
        Buffer buffer = new Buffer(6);
        int numProducers = 5;
        int numConsumers = 5;

        Thread[] producerThreads = new Thread[numProducers];
        Thread[] consumerThreads = new Thread[numConsumers];

        // Create and start producer threads
        for (int i = 0; i < numProducers; i++) {
            final int producerId = i;
            producerThreads[i] = new Thread(() -> {
                try {
                    for (int j = 1; j <= 10; j++) {
                        buffer.produce(j);
                        System.out.println("Producer " + producerId + " produced: " + j);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            producerThreads[i].start();
        }

        // Create and start consumer threads
        for (int i = 0; i < numConsumers; i++) {
            final int consumerId = i;
            consumerThreads[i] = new Thread(() -> {
                try {
                    for (int j = 1; j <= 10; j++) {
                        int item = buffer.consume();
                        System.out.println("Consumer " + consumerId + " consumed: " + item);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            consumerThreads[i].start();
        }


        // Join all producer threads
        for (int i = 0; i < numProducers; i++) {
            try {
                producerThreads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Join all consumer threads
        for (int i = 0; i < numConsumers; i++) {
            try {
                consumerThreads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}