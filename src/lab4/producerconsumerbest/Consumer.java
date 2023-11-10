package lab4.producerconsumerbest;

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
            buf.consume((int) (Math.random()*(M-1) + 1), id, this);
            try {
                sleep((int) (100));
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
