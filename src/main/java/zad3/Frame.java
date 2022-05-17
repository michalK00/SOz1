package zad3;

public class Frame {

    private int currentAppeal;
    private int counter;

    public Frame() {
        currentAppeal = -1;
        counter = 0;
    }

    public int getCurrentAppeal() {
        return currentAppeal;
    }

    public void setCurrentAppeal(int currentAppeal) {
        this.currentAppeal = currentAppeal;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

}
