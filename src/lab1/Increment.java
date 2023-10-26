package lab1;

public class Increment extends Thread{
    int i;
    int loops;
    public Increment(int loops, int counter){
        this.loops = loops;
        this.i = counter;
    }

    public void run(){
        for (int j = 0; j < this.loops; j++){
            Main.counter++;
        }
    }
}
