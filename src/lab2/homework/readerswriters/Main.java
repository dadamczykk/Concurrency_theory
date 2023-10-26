package lab2.homework.readerswriters;

public class Main {
    public static void main(String[] args) {
        ReadersWriters rw = new ReadersWriters();
        int numReaders = 100;
        int numWriters = 100;

        Thread[] readerThreads = new Thread[numReaders];
        Thread[] writerThreads = new Thread[numWriters];

        // Create and start reader threads
        for (int i = 0; i < numReaders; i++) {
            readerThreads[i] = new Thread(() -> {
                rw.startRead();
                System.out.println("Reading...");
                rw.endRead();
            });
            readerThreads[i].start();

            writerThreads[i] = new Thread(() -> {
                rw.startWrite();
                System.out.println("Writing...");
                rw.endWrite();
            });
            writerThreads[i].start();

        }

        // Create and start writer threads
//        for (int i = 0; i < numWriters; i++) {
//            ;
//        }

        // Join all reader threads
        for (int i = 0; i < numReaders; i++) {
            try {
                readerThreads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Join all writer threads
        for (int i = 0; i < numWriters; i++) {
            try {
                writerThreads[i].join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}