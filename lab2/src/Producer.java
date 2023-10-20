public class Producer implements Runnable {
    private Buffer buffer;

    private int id;

    public Producer(int id, Buffer buffer) {
        this.buffer = buffer;
        this.id = id;
    }

    public void run() {

        for(int i = 0; i < Main.ILOSC; i++) {

            buffer.put("Message id: " + i + " from producer with id " + this.id);
            System.out.println("[Producer id: " + this.id + "] Sending message " + i);
        }

    }
}
