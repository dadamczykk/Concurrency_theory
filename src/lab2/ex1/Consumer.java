package lab2.ex1;

public class Consumer implements Runnable {
    private final Buffer buffer;
    private final int id;

    public Consumer(int id, Buffer buffer) {
        this.buffer = buffer;
        this.id = id;
    }

    public void run() {

        for(int i = 0; i < Main.messagesNumber; i++) {
            Message message = buffer.consume();
            System.out.println("[Consumer id " + id +
                    "] " + message.toString());
        }

    }
}
