package lab6.comparetest;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Thread.sleep;

public class Main {
        public static long testTime = 180000;


        public static void main(String[] args) throws InterruptedException, IOException {
            BufferedWriter writer = new BufferedWriter(new FileWriter("CPUTimes.csv", false));
            writer.write("function,bufferSize,PKNumber,id,genName,CPUTime\n");
            writer.close();

            writer =  new BufferedWriter(new FileWriter("ExecutionsNumber.csv", false));
            writer.write("function,bufferSize,PKNumber,id,genName,ExecutionsNumber\n");
            writer.close();

            writer =  new BufferedWriter(new FileWriter("AverageTimes.csv", false));
            writer.write("function,bufferSize,PKNumber,chunkSize,genName,time\n");
            writer.close();



            performTest(10, 20, testTime, new Buffer3lock(20), ERandom.standard, 3);
            performTest(10, 20, testTime, new Buffer2cond(20), ERandom.standard, 3);
            performTest(10, 20, testTime, new Buffer4cond(20), ERandom.standard, 3);

            performTest(100, 200, testTime, new Buffer3lock(200), ERandom.standard, 3);
            performTest(100, 200, testTime, new Buffer2cond(200), ERandom.standard, 3);
            performTest(100, 200, testTime, new Buffer4cond(200), ERandom.standard, 3);

            performTest(100, 20, testTime, new Buffer3lock(20), ERandom.standard, 3);
            performTest(100, 20, testTime, new Buffer2cond(20), ERandom.standard, 3);
            performTest(100, 20, testTime, new Buffer4cond(20), ERandom.standard, 3);

            performTest(10, 200, testTime, new Buffer3lock(200), ERandom.standard, 3);
            performTest(10, 200, testTime, new Buffer2cond(200), ERandom.standard, 3);
            performTest(10, 200, testTime, new Buffer4cond(200), ERandom.standard, 3);

            performTest(10, 200, testTime, new Buffer3lock(200), ERandom.secure, 3);
            performTest(10, 200, testTime, new Buffer2cond(200), ERandom.secure, 3);
            performTest(10, 200, testTime, new Buffer4cond(200), ERandom.secure, 3);

            performTest(10, 200, testTime, new Buffer3lock(200), ERandom.thread, 3);
            performTest(10, 200, testTime, new Buffer2cond(200), ERandom.thread, 3);
            performTest(10, 200, testTime, new Buffer4cond(200), ERandom.thread, 3);
        }

    public static void performTest(int PKNumber, int bufferSize, long testTime,
                                   IBuffer buffer, ERandom randType, int seed) throws InterruptedException, IOException {
        String bufferName;
        if (buffer instanceof Buffer2cond) bufferName = "2cond";
        else if (buffer instanceof Buffer4cond) bufferName = "4cond";
        else bufferName = "3lock";


        Consumer[] consumers = new Consumer[PKNumber];
        Producer[] producers = new Producer[PKNumber];
        Thread[] consumersTh = new Thread[PKNumber];
        Thread[] producersTh = new Thread[PKNumber];

        int j = 0;
        while (j < PKNumber){
            consumers[j] = new Consumer(buffer, bufferSize, j, randType, seed);
            producers[j] = new Producer(buffer, bufferSize, j, randType, seed);

            consumersTh[j] = new Thread(consumers[j]);
            producersTh[j] = new Thread(producers[j]);

            producersTh[j].start();
            consumersTh[j].start();
            j++;
        }

        sleep(testTime);

        printCPUTime(bufferName, randType.name(), bufferSize, producersTh, consumersTh, PKNumber);

        sleep(200);

        for (int i=0; i < PKNumber; i++){
            producersTh[i].interrupt();
            consumersTh[i].interrupt();
        }

        printWaitingLoops(bufferName, randType.name(), bufferSize, producers, consumers, PKNumber);
        printAverageTimes(bufferName, randType.name(), bufferSize, producers, consumers, PKNumber);

        sleep(1000);
    }

    public static void printCPUTime(String name, String genName, int bufferSize,
                                    Thread[] producers, Thread[] consumers, int PKNumber) throws IOException {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        BufferedWriter writer = new BufferedWriter(new FileWriter("CPUTimes.csv", true));
        for (int i=0; i < PKNumber; i++){
            long consumerId = consumers[i].getId();
            long producerId = producers[i].getId();
            if ( !threadMXBean.isThreadCpuTimeSupported() ) {
                continue;
            }
            if ( !threadMXBean.isThreadCpuTimeEnabled() ) {
                threadMXBean.setThreadCpuTimeEnabled( true );
            }
            writer.write(name+"_producer"+","+bufferSize+","+PKNumber+","+i+","+genName+","
                    +threadMXBean.getThreadCpuTime(producerId)+"\n");
            writer.write(name+"_consumer"+","+bufferSize+","+PKNumber+","+i+","+genName+","
                    +threadMXBean.getThreadCpuTime(consumerId)+"\n");

        }
        writer.close();
    }



    public static void printWaitingLoops(String name, String genName, int bufferSize,
                                         Producer[] producers, Consumer[] consumers, int PKNumber) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("ExecutionsNumber.csv", true));

        for (int i = 0; i < PKNumber; i++){
            if (producers[i] != null){
                writer.write(name+"_producer,"+bufferSize+","+PKNumber+","+i+","+genName+","
                        +producers[i].loops+"\n");
            }
        }
        for (int i = 0; i < PKNumber; i++){
            if (consumers[i] != null) {
                writer.write(name+"_consumer,"+bufferSize+","+PKNumber+","+i+","+genName+","
                        +consumers[i].loops+"\n");
            }
        }
        writer.close();
    }

    public static void printAverageTimes(String name, String genName, int bufferSize,
                                         Producer[] producers, Consumer[] consumers, int PKNumber) throws IOException {

        List<List<Long>> getTimes = new ArrayList<>();
        List<List<Long>> putTimes = new ArrayList<>();
        for (int i = 0; i <= bufferSize; i++){
            getTimes.add(new ArrayList<>());
            putTimes.add(new ArrayList<>());
        }
        for (int i = 0; i < PKNumber; i++){
            for (int j = 0; j <= bufferSize; j++){
                getTimes.get(j).addAll(consumers[i].timesList.get(j));
                putTimes.get(j).addAll(producers[i].timesList.get(j));
            }
        }

        List<Double> getMeans = new ArrayList<>();
        List<Double> putMeans = new ArrayList<>();
        for (int i = 0; i <= bufferSize; i++){
            long getSum = 0;
            long putSum = 0;
            for (Long val : getTimes.get(i)){
                getSum += val;
            }
            for (Long val : putTimes.get(i)){
                putSum += val;
            }
            double getMean = (double) (!getTimes.get(i).isEmpty() ? getSum / getTimes.get(i).size() : 0);

            double putMean = (double) (!putTimes.get(i).isEmpty() ? putSum / putTimes.get(i).size() : 0);

            getMeans.add(getMean);
            putMeans.add(putMean);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter("AverageTimes.csv", true));
        for (int i = 1; i < bufferSize; i++){
            writer.write(name+"_consumer,"+bufferSize+","+PKNumber+","+i+","+genName+","+getMeans.get(i)+"\n");
            writer.write(name+"_producer,"+bufferSize+","+PKNumber+","+i+","+genName+","+putMeans.get(i)+"\n");
        }

        writer.close();

    }

}
