package lab2.ex1;

public class Main {
    // One producer, one consumer
    public static int messagesNumber = 1000;
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Thread consumer = new Thread(new Consumer(0, buffer));
        Thread producer = new Thread(new Producer(0, buffer));
        consumer.start();
        producer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
