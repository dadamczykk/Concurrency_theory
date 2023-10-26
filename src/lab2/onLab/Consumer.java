package lab2.onLab;

public class Consumer implements Runnable {
    private final Buffer buffer;
    private final int id;

    public Consumer(int id, Buffer buffer) {
        this.buffer = buffer;
        this.id = id;
    }

    public void run() {

        while (true){
//        for(int i = 0; i < Main.messagesNumber; i++) {
            int message = buffer.consume();
            System.out.println("[Consumer id " + id +
                    "] " + message);
        }

    }
}
