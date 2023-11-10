package lab3.ex1;


public class Main {
    public static int messagesNumber = 10;
    public static int bufferSize = 5;
    public static void main(String[] args) {
        Buffer buffer = new Buffer(bufferSize);
        int i = 0;

        while (true){
            i++;
            Thread consumer = new Thread(new Consumer(i, buffer));
            Thread producer = new Thread(new Producer(i, buffer));
            consumer.start();
            producer.start();
        }

//        for (int i = 0; i < threadsNumber; i++){
//            try {
//                producers[i].join();
//                consumers[i].join();
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }

    }
}
