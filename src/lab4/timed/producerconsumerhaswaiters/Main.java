package lab4.timed.producerconsumerhaswaiters;


import static java.lang.Thread.sleep;

public class Main {
        public static int messagesNumber = 2000;
        public static int threadsNumber = 5;
        public static int bufferSize = 10;

        public static Consumer[] consumers = new Consumer[threadsNumber];
        public static Producer[] producers = new Producer[threadsNumber];

        public static Thread[] consumersTh = new Thread[threadsNumber];
        public static Thread[] producersTh = new Thread[threadsNumber];

        public static void main(String[] args) throws InterruptedException {
            Buffer buffer = new Buffer(bufferSize);

            int j = 0;
            while (j < threadsNumber){
                consumers[j] = new Consumer(buffer, bufferSize, j);
                producers[j] = new Producer(buffer, bufferSize, j);
                consumersTh[j] = new Thread(consumers[j]);
                producersTh[j] = new Thread(producers[j]);

                producersTh[j].start();
                consumersTh[j].start();
                j++;
            }

            while(true){
                sleep(30);
                printWaitingLoops();
            }

        }

    public static void printWaitingLoops(){
        System.out.println("**** Number of productions / consumptions ****");
        for (int i = 0; i < threadsNumber; i++){
            if (producers[i] != null){
                System.out.println("Producer id: " + i + " number of executions: " + producers[i].loops);
            }
        }
        System.out.println("==========================================");
        for (int i = 0; i < threadsNumber; i++){
            if (consumers[i] != null) {
                System.out.println("Consumer id: " + i + " number of executions: " + consumers[i].loops);
            }
        }
        System.out.println("**** **** **** ****  ****  **** **** **** ****");
    }


}
