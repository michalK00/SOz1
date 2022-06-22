package zad5;

public class Process {
    private int arrivalTime;
    private int durationTime;
    private int cpuLoad;
    private boolean duringService;
    private int timeLeft;

    public Process(int arrivalTime, int durationTime, int cpuLoad) {
        this.arrivalTime = arrivalTime;
        this.cpuLoad = cpuLoad;
        this.durationTime = durationTime;
        duringService = false;
        timeLeft =  durationTime;
    }

    public int getDurationTime() {
        return durationTime;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public boolean isDuringService() {
        return duringService;
    }

    public void setDuringService(boolean duringService) {
        this.duringService = duringService;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(int cpuLoad) {
        this.cpuLoad = cpuLoad;
    }
}
