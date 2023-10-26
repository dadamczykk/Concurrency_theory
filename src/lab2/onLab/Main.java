package lab2.onLab;

import lab2.onLab.Consumer;
import lab2.onLab.Producer;

public class Main {
    // One producer, one consumer
    public static int messagesNumber = 10;
    public static int threadsNumber = 5;
    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Thread[] consumers = new Thread[threadsNumber];
        Thread[] producers = new Thread[threadsNumber];
        for (int i = 0; i < threadsNumber; i++){
            consumers[i] = new Thread(new Consumer(i, buffer));
            producers[i] = new Thread(new Producer(i, buffer));
            consumers[i].start();
            producers[i].start();

        }
        for (int i = 0; i < threadsNumber; i++){
            try {
                producers[i].join();
                consumers[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
