package zad2;


public class Request {

    private int arrivalTime;
    private int address;
    private int waitingTime;
    private boolean served;
    private int completionTime;
    private boolean realTimeRequest;
    private int initialDeadline;
    private int deadline;
    private boolean failed;

    public Request(int arrivalTime, int address) {
        this.arrivalTime = arrivalTime;
        this.address = address;
        deadline = 0;
        waitingTime = 0;
        served = false;
        completionTime = 0;
        realTimeRequest = false;
        failed = false;
    }

    public Request(int arrivalTime, int address, int deadline){
        this.arrivalTime = arrivalTime;
        this.address = address;
        this.deadline = deadline;
        initialDeadline = deadline;
        waitingTime = 0;
        served = false;
        completionTime = 0;
        realTimeRequest = true;
        failed = false;
    }

    @Override
    public String toString() {
        if(realTimeRequest){
            return "RealTimeRequest{" +
                    "arrivalTime=" + arrivalTime +
                    ", address=" + address +
                    ", waitingTime=" + waitingTime +
                    ", served=" + served +
                    ", completionTime=" + completionTime +
                    ", deadline=" + deadline +
                    ", initialDeadline=" + initialDeadline +
                    ", failed=" + failed +
                    '}';
        } else {
            return "Request{" +
                    "arrivalTime=" + arrivalTime +
                    ", address=" + address +
                    ", waitingTime=" + waitingTime +
                    ", served=" + served +
                    ", completionTime=" + completionTime +
                    '}';
        }
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public boolean isRealTimeRequest() {
        return realTimeRequest;
    }

    public int getInitialDeadline() {
        return initialDeadline;
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

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }
}
