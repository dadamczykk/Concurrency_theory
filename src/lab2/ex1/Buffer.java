package lab2.ex1;

public class Buffer {
    private Message message;
    private boolean isEmpty = true;

    public synchronized void put(Message message) {
        while (!isEmpty) {
            try {
                wait(); // Wait until buffer is not full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.message = message;
        isEmpty = false;
        notify(); // show consumer, that buffer is empty
    }

    public synchronized Message take() {
        while (isEmpty) {
            try {
                wait(); // Wait until buffer is not empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Message takenMessage = message;
        isEmpty = true;
        notify(); // Inform producer that buffer is not empty
        return takenMessage;
    }
}
