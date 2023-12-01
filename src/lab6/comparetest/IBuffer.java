package lab6.comparetest;

public interface IBuffer {
    public void produce(int toProduce, int id);
    public void consume(int toConsume, int id);
}
