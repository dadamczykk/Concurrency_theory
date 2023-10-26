package lab2.ex1;

public class Producer implements Runnable {
    private final Buffer buffer;

    private final int id;

    public Producer(int id, Buffer buffer) {
        this.buffer = buffer;
        this.id = id;
    }

    public void run() {

        for(int i = 0; i < Main.messagesNumber; i++) {

            buffer.produce(new Message(id, i, "some text"));
            System.out.println("[Producer id " + this.id + "] Sending message " + i);
        }

    }
}
