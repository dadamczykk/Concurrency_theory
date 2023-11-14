package lab3.ex2;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Producer implements Runnable {
    private final Buffer buffer;

    private final int id;
    public int waitLoops = 0;
    public Producer(int id, Buffer buffer) {
        this.buffer = buffer;
        this.id = id;
    }



    public void run() {
        int M = Main.bufferSize;
        while(true) {
//            waitLoops++;
            int val;
            if (this.id == 1){
                val = M;
            } else {
                val = (int) (Math.random()*(M-1)+1);
            }

            buffer.put(val, this);
//            try {
//                sleep((int) (100));
//            } catch (InterruptedException e) {
//                System.out.println(e.getMessage());
//            }
        }
    }

    public int getWaitLoops(){
        return waitLoops;
    }
}
