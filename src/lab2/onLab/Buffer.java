package lab2.onLab;

public class Buffer {
    private int message;
    private boolean isEmpty = true;

    public synchronized void produce(int message) {
        while (!isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.message = message;
        isEmpty = false;
        notify();
    }

    public synchronized int consume() {
        while (isEmpty) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        int takenMessage = message;
        isEmpty = true;
        notify();
        return takenMessage;
    }
}
