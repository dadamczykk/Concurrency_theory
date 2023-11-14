package lab4.producerconsumer2cond;

public class Consumer extends Thread {

    private Buffer buf;
    private final int M;
    private final int id;
    public int loops;

    public Consumer(Buffer buf, int M, int id){
        this.buf = buf;
        this.M = M;
        this.id = id;
    }

    public void run(){
        while(true) {
            loops++;
            int val;

            if (this.id == 0) { val = M-1; }

            else{ val = (int) (Math.random()*(M-1)+1); }

            buf.consume(val, id);


        }
    }
}
