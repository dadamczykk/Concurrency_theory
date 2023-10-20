public class Main{

    public static int counter = 0;
    public static void main(String[] args) throws InterruptedException {
//        int
        int decrement_threads = 10000;
        int increment_threads = 10000;
        int decrement_loops = 10000;
        int increment_loops = 10000;
        Decrement[] decarr = new Decrement[decrement_threads];
        Increment[] incarr = new Increment[increment_threads];


        for (int i = 0; i < decrement_threads; i++){
            Decrement th = new Decrement(decrement_loops, counter);
            decarr[i] = th;
            th.start();
        }
        for (int i = 0; i < increment_threads; i++){
            Increment th = new Increment(increment_loops, counter);
            incarr[i] = th;
            th.start();
        }
        for (int i = 0; i < decrement_threads; i++){
            decarr[i].join();
        }
        for (int i = 0; i < increment_threads; i++){
            incarr[i].join();
        }
        System.out.println(counter);
    }

}