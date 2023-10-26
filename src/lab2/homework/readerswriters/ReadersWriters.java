package lab2.homework.readerswriters;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ReadersWriters {
    private int readersCount = 0;
    private boolean isWriting = false;
    private final Lock lock = new ReentrantLock();
    private final Condition noWriters = lock.newCondition();
    private final Condition noReaders = lock.newCondition();
    private int waitingWriters = 0;
    private int waitingReaders = 0;

    public void startRead() {
        lock.lock();
        try {
            while (isWriting) {
                waitingReaders++;
                noReaders.await();
                waitingReaders--;

            }
            readersCount++;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void endRead() {
        lock.lock();
        try {
            readersCount--;
            if (readersCount == 0) {
                noWriters.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void startWrite() {
        lock.lock();
        try {
            while (isWriting || readersCount > 0) {
                try {
                    waitingWriters++;
                    noWriters.await();
                    waitingWriters--;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            isWriting = true;
        } finally {
            lock.unlock();
        }
    }

    public void endWrite() {
        lock.lock();
        try {
            isWriting = false;
            System.out.println(waitingWriters + " " +  waitingReaders + " " + isWriting);
            if (waitingReaders == 0){
                noWriters.signalAll();
            } else{
                noReaders.signalAll();

            }

        } finally {
            lock.unlock();
        }
    }
}