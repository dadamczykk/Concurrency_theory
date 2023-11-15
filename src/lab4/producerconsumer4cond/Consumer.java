package lab4.producerconsumer4cond;

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

            // normal
//             if (false){ }

            // consumer id 0 starvation
             if (this.id == 0) { val = M-1; }

            // deadlock situation
//            if (this.id < 3){ val = M-1; }

            else{ val = (int) (Math.random()*(M-1)+1); }

            buf.consume(val, id);

            try {
                sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
