package lab2.onLab;

public class Producer implements Runnable {
    private final Buffer buffer;

    private final int id;

    public Producer(int id, Buffer buffer) {
        this.buffer = buffer;
        this.id = id;
    }

    public void run() {

        int i = 0;
        while (true){
//        for(int i = 0; i < Main.messagesNumber; i++) {

            buffer.produce(i);
            System.out.println("[Producer id " + this.id + "] Sending message " + i);
            i++;
        }

    }
}
