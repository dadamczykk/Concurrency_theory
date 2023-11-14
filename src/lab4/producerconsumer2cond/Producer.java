package lab4.producerconsumer2cond;

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

            if (this.id == 0) { val = M-1; }
            else { val = (int) (Math.random()*(M-1)+1); }

            int[] a = new int[val];
            for(int i=0; i<a.length; i++) {
                a[i] = (int)(Math.random() * 10) + 1;
            }
            buf.produce(a, id);


        }
    }
}