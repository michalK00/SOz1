public class Process{

    private int arrivalTime;
    private int processingTime;
    private int progress;
    private int waitingTime;
    private boolean finished;
    private int completionTime;

    public Process(int arrivalTime, int processingTime) {
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        progress = 0;
        waitingTime = 0;
        finished = false;
        completionTime = 0;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int arrivalTimeComparator(Process o) {
        int comparedArrivalTime= o.getArrivalTime();
        return this.arrivalTime-comparedArrivalTime;
    }

    public int processingTimeComparator(Process o){
        int comparedProcessTime = o.getProcessingTime();
        return this.processingTime-comparedProcessTime;
    }


    @Override
    public String toString() {
        return "Process{" +
                "arrivalTime=" + arrivalTime +
                ", processingTime=" + processingTime +
                ", progress=" + progress +
                ", waitingTime=" + waitingTime +
                ", finished=" + finished +
                '}';
    }
}
