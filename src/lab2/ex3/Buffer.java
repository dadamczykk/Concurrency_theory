package lab2.ex3;

import lab2.ex1.Message;

public class Buffer {
    private Message message;
    private boolean isEmpty = true;

    public synchronized void put(Message message) {

//        System.out.println("inside put");
        while (!isEmpty) {
            try {
//                System.out.println("not empty");
                wait(); // Wait until buffer is not full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.message = message;
        isEmpty = false;
        notifyAll(); // show consumer, that buffer is empty
    }

    public synchronized Message take() {
//        System.out.println("inside take");
        while (isEmpty) {
            try {
//                System.out.println("is empty");
                wait(); // Wait until buffer is not empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Message takenMessage = message;
        isEmpty = true;
        notifyAll(); // Inform producer that buffer is not empty
        return takenMessage;
    }
}
