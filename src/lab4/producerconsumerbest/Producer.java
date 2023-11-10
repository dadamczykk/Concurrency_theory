package lab4.producerconsumerbest;

public class Producer extends Thread {
    private Buffer buf;
    private final int M;

    private final int id;

    public int loops;

    public Producer(Buffer buf, int M, int id)  {
        this.buf = buf;
        this.M = M;
        this.id = id;

    }

    public void run() {
        int a[] = new int[(int) (Math.random()*(M-1)+1)];
        for(int i=0; i<a.length; i++) {
            a[i] = (int)(Math.random() * 10) + 1;
        }

        while(true) {
            buf.produce(a, id, this);
            try {
                sleep((int) (Math.random() * 100));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}