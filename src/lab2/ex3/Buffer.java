package lab2.ex3;

import lab2.ex1.Message;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
    private final List<Message> messages;
    private final int bufferSize;
    private int messagesInBuffer;

    public Buffer(int bufferSize){
        this.bufferSize = bufferSize;
        this.messages = new ArrayList<>(bufferSize);
    }

    public synchronized void put(Message message) {

        while (messagesInBuffer == bufferSize) {
            try {
                wait(); // Wait until buffer is not full
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.messages.add(message);
        this.messagesInBuffer++;
        notifyAll(); // show consumer, that buffer is empty
    }

    public synchronized Message take() {
        while (messagesInBuffer == 0) {
            try {
                wait(); // Wait until buffer is not empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        messagesInBuffer--;
        Message takenMessage = messages.remove(messagesInBuffer);
        notifyAll(); // Inform producer that buffer is not empty
        return takenMessage;
    }
}
