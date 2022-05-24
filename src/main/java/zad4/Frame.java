package zad4;

public class Frame {

    private int currentAppeal;
    private int counter;
    private int currentProcess;

    public Frame() {
        currentAppeal = -1;
        currentProcess = -1;
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

    public int getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(int currentProcess) {
        this.currentProcess = currentProcess;
    }
}
