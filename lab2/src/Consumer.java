public class Consumer implements Runnable {
    private Buffer buffer;
    private int id;

    public Consumer(int id, Buffer buffer) {
        this.buffer = buffer;
        this.id = id;
    }

    public void run() {

        for(int i = 0; i < Main.ILOSC; i++) {
            String message = buffer.take();
            System.out.println("[Consumer id: " + id + "] " +message);
        }

    }
}
