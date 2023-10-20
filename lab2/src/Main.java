public class Main {
    static int ILOSC = 500; // Zdefiniuj liczbę wiadomości do wyprodukowania i skonsumowania
    static int ILOSCWATKOW = 1000;

    public static void main(String[] args) {
        Buffer buffer = new Buffer();
        Thread[] consumerThreads = new Thread[ILOSCWATKOW];
        Thread[] producerThreads = new Thread[ILOSCWATKOW];

        for (int i = 0; i < ILOSCWATKOW; i++ ) {
            producerThreads[i] = new Thread(new Producer(i, buffer));
            consumerThreads[i] = new Thread(new Consumer(i, buffer));
            producerThreads[i].start();
            consumerThreads[i].start();
        }

        for (int i = 0; i < ILOSC; i++){
            try {
                producerThreads[i].join();
                consumerThreads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
