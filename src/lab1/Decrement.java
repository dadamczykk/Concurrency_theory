package lab1;

public class Decrement extends Thread{
    int i;
    int loops;
    public Decrement(int loops, int counter){
        this.loops = loops;
        this.i = counter;
    }

    public void run(){
        for (int j = 0; j < this.loops; j++){
            Main.counter--;
        }
    }
}

