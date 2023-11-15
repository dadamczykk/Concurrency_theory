package lab4.producerconsumer4cond;

public class Producer extends Thread {
    private final Buffer buf;
    private final int M;

    private final int id;

    public int loops;

    public Producer(Buffer buf, int M, int id)  {
        this.buf = buf;
        this.M = M;
        this.id = id;

    }

    public void run() {


        while(true) {
            loops++;
            int val;

             // normal
             if (false){ }

            // producer id 0 starvation
//             if (this.id == 0) { val = M-1; }

            // deadlock situation
//            if (this.id < 3){ val = 1; }
            else { val = (int) (Math.random()*(M-1)+1); }

            int[] a = new int[val];
            for(int i=0; i<a.length; i++) {
                a[i] = (int)(Math.random() * 10) + 1;
            }
            buf.produce(a, id);

            try {
                sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}