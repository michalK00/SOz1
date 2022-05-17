package zad3.DataStructures;

public class OneWayLinkedQueue<E> implements OneWayLinkedQueueInterface<E> {


    Element head = null;
    Element tail = head;

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public void clear() {
        head = null;
    }

    @Override
    public void enqueue(E elem) {
        Element newElem = new Element(elem);
        if(isEmpty()){
            head = newElem;
        } else {
            tail.setNext(newElem);
        }
        tail = newElem;
    }

    @Override
    public E dequeue() throws Exception {
        if(isEmpty()){
            throw new EmptyQueueException("Queue is empty");
        }
        Element returnedElement;
        returnedElement = head;
        head = head.getNext();
        return returnedElement.getValue();
    }

    @Override
    public int size() {
        int pos = 0;
        Element currentElement = head;
        while(currentElement!=null){
            pos++;
            currentElement=currentElement.getNext();
        }
        return pos;
    }
    public void printQueue(){
        Element currentElement = head;
        while(currentElement!=null){
            System.out.println(currentElement.getValue());
            currentElement=currentElement.getNext();
        }
    }

    private class Element{

        private E value;
        private Element next;

        public E getValue() {
            return value;
        }
        public void setValue(E value) {
            this.value = value;
        }
        public Element getNext() {
            return next;
        }
        public void setNext(Element next) {
            this.next = next;
        }
        Element(E data){
            this.value=data;
        }
    }

}
class EmptyQueueException extends Exception{
    public EmptyQueueException(String message) {
        super(message);
    }

}
