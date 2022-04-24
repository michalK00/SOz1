package zad2;


public class Request {

    private int arrivalTime;
    private int address;
    private int waitingTime;
    private boolean served;
    private int completionTime;

    public Request(int arrivalTime, int address) {
        this.arrivalTime = arrivalTime;
        this.address = address;
        waitingTime = 0;
        served = false;
        completionTime = 0;
    }

    @Override
    public String toString() {
        return "Request{" +
                "arrivalTime=" + arrivalTime +
                ", address=" + address +
                ", waitingTime=" + waitingTime +
                ", served=" + served +
                ", completionTime=" + completionTime +
                '}';
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public boolean isServed() {
        return served;
    }

    public void setServed(boolean served) {
        this.served = served;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }
}
