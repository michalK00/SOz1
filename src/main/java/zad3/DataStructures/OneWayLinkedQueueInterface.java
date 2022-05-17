package zad3.DataStructures;

public interface OneWayLinkedQueueInterface<E> {

    public boolean isEmpty();

    public void clear();

    public void enqueue(E elem);

    public E dequeue() throws Exception;

    public int size();

}
