public class Buffer {
    private String message;
    private boolean isEmpty = true;

    public synchronized void put(String message) {
        while (!isEmpty) {
            try {
                wait(); // Oczekuj, jeśli bufor jest pełny
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        this.message = message;
        isEmpty = false;
        notifyAll(); // Powiadom konsumenta, że bufor jest teraz pełny
    }

    public synchronized String take() {
        while (isEmpty) {
            try {
                wait(); // Oczekuj, jeśli bufor jest pusty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        String takenMessage = message;
        isEmpty = true;
        notifyAll(); // Powiadom producenta, że bufor jest teraz pusty
        return takenMessage;
    }
}
