package zad3.DataStructures;


import java.util.ArrayList;

public class AppealQueue {

    ArrayList<Appeal> queue = new ArrayList<>();
    int numberOfFrames;

    public AppealQueue(int numberOfFrames) {
        this.numberOfFrames = numberOfFrames;
    }

    public void add(int value){
        if(queue.size() < numberOfFrames){
            queue.add(new Appeal(value));
        } else{
            queue.remove(0);
            for (int x = 0; x<queue.size(); x++) {
                setBit(x, false);
            }
            queue.add(new Appeal(value));
        }
    }
    public void add(Appeal appeal){
        if(queue.size() < numberOfFrames){
            queue.add(appeal);
        } else{
            queue.remove(0);
            queue.add(appeal);
        }
    }

    public void setBit(int index, boolean switchTo){
        queue.get(index).bit = switchTo;
    }

    public void dequeue(){
        queue.remove(0);
    }

    public int getValue(int index){
        return queue.get(index).value;
    }
    public boolean getBit(int index){
        return queue.get(index).bit;
    }
    public Appeal getAppeal(int index){
        return queue.get(index);
    }


    public int indexOf(int value){
        for(int x = 0; x<numberOfFrames; x++){
            if(queue.get(x).value == value){
                return x;
            }
        }
        return -1;
    }
    public int size(){
        return queue.size();
    }

    public class Appeal {
        int value;
        boolean bit;

        public Appeal(int value) {
            this.value = value;
            bit = true;
        }



    }

}
